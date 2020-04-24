package server
import Server.ReviewingServices
import java.io.OutputStream
import java.net.ServerSocket
import java.net.Socket
import java.nio.charset.Charset
import java.time.Year
import java.util.*
import kotlin.concurrent.thread
fun main() {
    val server = ServerSocket(9999)
    println("Server is running on port ${server.localPort}")
    while (true) {
        val client = server.accept()
        println("Client connected: ${client.inetAddress.hostAddress}")
        // Run client in it's own thread.
        thread { ClientHandler(client).run() }
    }
}
class ClientHandler(
    private val client: Socket,
    private val reader: Scanner,
    private val writer: OutputStream
) {
    constructor(client: Socket): this(
        client = client,
        reader = Scanner(client.getInputStream()),
        writer = client.getOutputStream()
    )
    private val reviewingServices : ReviewingServices = ReviewingServices()
    private var running: Boolean = false
    fun run() {
        running = true
        write(
            """
                |Welcome to the server!
                |To Exit, write: 'EXIT'.
                |Available operations: add, review, comment, view   
                |Examples:
                |  add      gameName releaseYear
                |  review   gameName rating
                |  comment  gameName message
                |  view     gameName reviews
                |  view     gameName comments
            """.trimMargin()
        )
        while (running) {
            try {
                val text = reader.nextLine()
                if (text == "EXIT"){
                    shutdown()
                    continue
                }
                val values = text.split(' ')
                val result = when (values[0]) {
                    "add" -> if (values.size < 3) "Missing name or release year" else reviewingServices.addGame(values[1], Year.parse(values[2])).toString()
                    "review" -> if (values.size < 3) "Missing name or release year" else reviewingServices.reviewGame(values[1], values[2].toFloat(), client.toString())
                    "comment" -> if (values.size < 3) "Missing name or release year" else reviewingServices.commentGame(values[1], values.joinToString(separator = " "), client.toString())
                    "view" -> if (values.size < 3) "Missing name or release year"
                            else when (values[2]) {
                                    "reviews"   -> reviewingServices.viewReviews(values[1])
                                    "comments"  -> reviewingServices.viewComments(values[1])
                                    else        -> "Invalid Command"
                                    }
                    else -> throw UnsupportedOperationException("Operation not supported!")
                }
                if (result != null) {
                    write(result)
                }
                else {
                    write("Game not found!")
                }
            } catch (ex: Exception) {
                println(ex.message)
                shutdown()
            }
        }
    }
    private fun write(message: String) {
        writer.write((message + '\n').toByteArray(Charset.defaultCharset()))
    }
    private fun shutdown() {
        running = false
        client.close()
        println("${client.inetAddress.hostAddress} closed the connection")
    }
}