package com.android.tools.idea.gradle.project.model

fun NdkModuleModel.fetchVariantNames() : Collection<String?> {
    return try {
        ndkVariantNames
    } catch (_: NoSuchMethodError) {
        // Method is no longer accessible so try to get it via reflection
        val variants = javaClass.methods.find { it.name == "getAllVariantAbis" }?.invoke(this) as? Set<*>
        //  This is a set of AbiVariant, which is not visible either, so find the right field with the name property
        variants
            ?.filterNotNull()
            ?.map { it.javaClass.getMethod("getVariant").invoke(it) } as? Collection<String?> ?: emptySet()
    }
}
