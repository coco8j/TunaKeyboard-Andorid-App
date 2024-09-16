#!/usr/bin/env python3.10
# encoding: utf-8

"""generate_spellcheck_config.py: Simple helper script for auto generating the spell check config

   The reason why we need to do this is because we want the language to be chosen dynamically based on the active
   keyboard subtype, and Android ignores the FlorisBoard spell checker unless it declares the currently active system
   language. As such we take the list of languages from an emulated or physical device and generate a spell checker
   config with subtypes for all languages.

   Before running this script, make sure that exactly one device is connected and that FlorisBoard Debug (0.4.0-alpha1
   or newer) is installed. Then go to Settings > Devtools > System locales and tap the save icon in the top right
   corner. This creates a tsv file with all the devices in the interal app storage, which this script can now read.
"""

from datetime import datetime
import os
import subprocess

PULL_CMD = "adb shell".encode("utf-8")
PULL_CMD_INPUT = """run-as dev.patrickgold.florisboard.debug
cat no_backup/devtools/system_locales.tsv
exit""".encode("utf-8")

GET_ANDROID_VERSION_CMP = "adb shell getprop ro.build.version.release".encode("utf-8")
GET_SDK_VERSION_CMP = "adb shell getprop ro.build.version.sdk".encode("utf-8")

XML_CONFIG_PATH = os.path.join(os.path.dirname(__file__), "../app/src/main/res/xml/spellchecker.xml")

XML_CONFIG_HEADER = \
"""<?xml version="1.0" encoding="utf-8"?>
<!-- Auto-generated by "utils/{file_name}" on {android_sdk_version} - DO NOT EDIT BY HAND!! -->
<!-- Last update: {timestamp} -->
<spell-checker xmlns:android="http://schemas.android.com/apk/res/android"
    android:label="@string/floris_app_name"
    android:settingsActivity="dev.patrickgold.florisboard.SettingsLauncherAlias">
"""

XML_CONFIG_SUBTYPE = \
"""    <subtype android:label="@string/general__system_string_placeholder" android:subtypeLocale="{lang_code}"/>
"""

XML_CONFIG_FOOTER = \
"""</spell-checker>
"""

def get_sdk_version() -> str:
    version = subprocess.run(GET_ANDROID_VERSION_CMP, shell=True, capture_output=True).stdout.decode().strip()
    sdk = subprocess.run(GET_SDK_VERSION_CMP, shell=True, capture_output=True).stdout.decode().strip()
    return f"Android {version} (API {sdk})"

def get_language_list() -> list[str]:
    ret = subprocess.run(PULL_CMD, shell=True, capture_output=True, input=PULL_CMD_INPUT)
    ret_stderr = ret.stderr.decode()
    if (len(ret_stderr) > 0):
        raise Exception(ret_stderr)
    else:
        tsvFile = ret.stdout.decode()
        lang_code_list = list()
        for line in tsvFile.splitlines():
            lang_code = line.split('\t')[0]
            if (lang_code.find('-') < 0):
                lang_code_list.append(lang_code)
        return lang_code_list

def write_spellcheck_config(path: str, lang_code_list: list[str]):
    with open(path, 'wt') as config_file:
        config_file.write(XML_CONFIG_HEADER.format(
            file_name=os.path.basename(__file__),
            android_sdk_version=get_sdk_version(),
            timestamp=datetime.now().replace(microsecond=0).isoformat(),
        ))
        for lang_code in lang_code_list:
            config_file.write(XML_CONFIG_SUBTYPE.format(lang_code=lang_code))
        config_file.write(XML_CONFIG_FOOTER)

def main():
    lang_code_list = get_language_list()
    write_spellcheck_config(XML_CONFIG_PATH, lang_code_list)

if __name__ == "__main__":
    main()
