package com.android.tools.idea.gradle.variant.view

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.modules

fun BuildVariantUpdater.update(project: Project, moduleName: String, selectedBuildVariant: String) =
    this.updateSelectedBuildVariant(project.modules.first { it.name == moduleName }, selectedBuildVariant)