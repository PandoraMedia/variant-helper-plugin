/*
 * Copyright 2019 Pandora Media, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See accompanying LICENSE file or you may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pandora.plugin.actions

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.VerticalFlowLayout
import com.intellij.openapi.util.SystemInfo
import com.intellij.ui.ScrollPaneFactory
import java.awt.Dimension
import javax.swing.JComboBox
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.SwingConstants

internal const val DIALOG_SIZE = 500

class MultiDropdownSelector(
    items: Set<List<String>>,
    project: Project,
    title: String
) : DialogWrapper(project) {
    private var dropDowns = mutableListOf<JComboBox<String>>()

    private val allItems = items

    val selectedItems: List<String>
        get() = dropDowns.map { it.selectedItem as String }

    init {
        this.title = title
        if (!SystemInfo.isMac) {
            setButtonsAlignment(SwingConstants.CENTER)
        }
        init()
    }

    override fun createCenterPanel(): JComponent? {
        val messagePanel = JPanel(VerticalFlowLayout(VerticalFlowLayout.TOP or VerticalFlowLayout.LEFT, true, false))
        messagePanel.maximumSize = Dimension(DIALOG_SIZE, DIALOG_SIZE)

        var i = 0
        allItems.map { it.toTypedArray() }.forEach {
            JComboBox(it).apply {
                dropDowns.add(this)
                messagePanel.add(this, i++)
            }
        }
        return ScrollPaneFactory.createScrollPane(messagePanel)
    }

    companion object {
        fun showMultiDropdownSelector(
            items: Set<List<String>>,
            project: Project,
            title: String
        ): List<Any> {
            val dialog = MultiDropdownSelector(items, project, title)
            dialog.show()

            return if (dialog.isOK) dialog.selectedItems else emptyList()
        }
    }
}
