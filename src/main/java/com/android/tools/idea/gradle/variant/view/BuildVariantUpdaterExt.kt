package com.android.tools.idea.gradle.variant.view

import com.intellij.openapi.project.Project

fun BuildVariantUpdater.update(project: Project, moduleName: String, selectedBuildVariant: String) =
    this.updateSelectedBuildVariant(project, moduleName, selectedBuildVariant)