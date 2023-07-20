package cc.unitmesh.devti.intentions.editor

import cc.unitmesh.devti.gui.DevtiFlowToolWindowFactory
import cc.unitmesh.devti.gui.chat.ChatBotActionType
import cc.unitmesh.devti.gui.chat.ChatCodingComponent
import cc.unitmesh.devti.gui.chat.ChatCodingService
import cc.unitmesh.devti.provider.ContextPrompter
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindowManager

fun sendToChat(project: Project, actionType: ChatBotActionType, prompter: ContextPrompter) {
    val toolWindowManager =
        ToolWindowManager.getInstance(project).getToolWindow(DevtiFlowToolWindowFactory.id) ?: return
    val chatCodingService = ChatCodingService(actionType)
    val contentPanel = ChatCodingComponent(chatCodingService)
    val contentManager = toolWindowManager.contentManager
    val content = contentManager.factory.createContent(contentPanel, chatCodingService.getLabel(), false)

    contentManager.removeAllContents(true)
    contentManager.addContent(content)

    toolWindowManager.activate {
        chatCodingService.handlePromptAndResponse(contentPanel, prompter)
    }
}