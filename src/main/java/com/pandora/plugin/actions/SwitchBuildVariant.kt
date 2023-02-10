package com.pandora.plugin.actions

import com.android.tools.idea.gradle.project.ProjectStructure
import com.android.tools.idea.gradle.project.model.AndroidModuleModel
import com.android.tools.idea.gradle.project.model.NdkModuleModel
import com.android.tools.idea.gradle.project.model.fetchVariantNames
import com.android.tools.idea.gradle.variant.view.BuildVariantUpdater
import com.android.tools.idea.gradle.variant.view.update
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleManager
import com.jetbrains.rd.util.firstOrNull

private const val title = "Select Build Variants:"

class SwitchBuildVariant : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return

        val structure = ProjectStructure.getInstance(project)
        val updater = BuildVariantUpdater.getInstance(project)
        val moduleManager = ModuleManager.getInstance(project)

        val knownLeafModules = structure.leafHolderModules
        val groupings = moduleManager.modules.map { it.variantItems }
            .filterNot { it.buildVariants.isEmpty() }
            .associateBy { it.buildVariants }

        val dialogResult = MultiDropdownSelector.showMultiDropdownSelector(groupings.keys, project, title)
        if (dialogResult.isEmpty()) {
            return
        }
        //Let Gradle and Android Studio give their best effort for determine all the necessary changes, first.
        knownLeafModules.forEach { module ->
            module.variantItems.buildVariants.findSelection(dialogResult)?.run {
                updater.update(project, module.name, this)
            }
        }

        moduleManager.modules.forEach { module ->
            module.variantItems.buildVariants.findSelection(dialogResult)?.run {
                updater.update(project, module.name, this)
            }
        }
    }

    private val Module.variantNames: Collection<String?>
        get() = NdkModuleModel.get(this)?.fetchVariantNames() ?: emptyList()

    private val Module.variantItems: ModuleBuildVariant
        get() = ModuleBuildVariant(name, variantNames.asSequence().distinct().filterNotNull().sorted().toList())

    private data class ModuleBuildVariant(val moduleName: String, val buildVariants: List<String>)
}

/**
 * First try to match the full-list of variants to get the selected key for that list, then fallback to a best effort.
 */
private fun List<String>.findSelection(dialogResult: Map<String, List<String>>) =
    dialogResult.entries.firstOrNull { this == it.value }?.key ?: firstOrNull { dialogResult.keys.contains(it) }
