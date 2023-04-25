import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.glfw.GLFW.*

fun main() {
    val width = 640
    val height = 480

    val taskRunners = TaskRunners(rasterTaskRunner = TaskRunner(), uiTaskRunner = TaskRunner())
    val glView = GLView(width, height)
    val shell = Shell(taskRunners, glView, null)

    taskRunners.rasterTaskRunner.postTask {
        println("in rasterThread")
        val context = shell.glView.createContext()
        val rasterizer = Rasterizer(width, height, context)
        shell.rasterizer = rasterizer
    }

    var keyPressed = false

    glView.setkeyCallback { _, key, _, action, _ ->
        if (key == GLFW_KEY_M && action == GLFW_PRESS) {
            keyPressed = true
        }
    }

    while (!shell.glView.windowShouldClose()) {
        if (keyPressed) {
            keyPressed = false
            shell.taskRunners.rasterTaskRunner.postTask {
                shell.rasterizer!!.drawToSurface()
                shell.glView.swapBuffers()
            }
        }
        shell.glView.pollEvents()
    }
    shell.taskRunners.terminateAll()
}
