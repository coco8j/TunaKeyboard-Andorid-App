/*
 * Copyright (C) 2021 Patrick Goldinger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("NOTHING_TO_INLINE")

package org.florisboard.lib.android

import android.content.Context
import androidx.annotation.StringRes
import org.florisboard.lib.kotlin.CurlyArg
import org.florisboard.lib.kotlin.curlyFormat
import kotlin.reflect.KClass

/**
 * Gets the system service instance of given class by first resolving its name and then
 * passing it to the system service resolver of this context.
 *
 * @param kClass The class of the system service instance which should be returned.
 *
 * @return The system service instance.
 *
 * @throws NullPointerException if no name can be found for given class.
 * @throws ClassCastException if the context returns a different class for resolved service name.
 */
@Throws(NullPointerException::class, ClassCastException::class)
fun <T : Any> Context.systemService(kClass: KClass<T>): T {
    val serviceName = this.getSystemServiceName(kClass.java)!!
    @Suppress("UNCHECKED_CAST")
    return this.getSystemService(serviceName) as T
}

/**
 * Gets the system service instance of given class by first resolving its name and then
 * passing it to the system service resolver of this context.
 *
 * @param kClass The class of the system service instance which should be returned.
 *
 * @return The system service instance or null if no service instance for passed class is available.
 */
fun <T : Any> Context.systemServiceOrNull(kClass: KClass<T>): T? {
    return try {
        this.systemService(kClass)
    } catch (e: Exception) {
        null
    }
}

/**
 * Return the string value associated with a particular resource ID. It will be stripped of any styled text
 * information.
 *
 * @param id The desired resource identifier, as generated by the aapt tool. This integer encodes the package, type,
 *  and resource entry. The value `0` is an invalid identifier.
 *
 * @return The string data associated with the resource, stripped of styled text information.
 *
 * @throws android.content.res.Resources.NotFoundException Throws NotFoundException if the given ID does not exist.
 */
@Throws(android.content.res.Resources.NotFoundException::class)
inline fun Context.stringRes(@StringRes id: Int): String {
    return this.resources.getString(id)
}

/**
 * Return the string value associated with a particular resource ID, substituting the format arguments as defined in
 * [String.curlyFormat]. It will be stripped of any styled text information.
 *
 * @param id The desired resource identifier, as generated by the aapt tool. This integer encodes the package, type,
 *  and resource entry. The value `0` is an invalid identifier.
 * @param args The format arguments that will be used for substitution.
 *
 * @return The string data associated with the resource, stripped of styled text information.
 *
 * @throws android.content.res.Resources.NotFoundException Throws NotFoundException if the given ID does not exist.
 */
@Throws(android.content.res.Resources.NotFoundException::class)
inline fun Context.stringRes(@StringRes id: Int, vararg args: CurlyArg): String {
    return this.resources.getString(id).curlyFormat(*args)
}
