import java.io.File
import scala.io.Source


object SrcInspector extends App {
    if (args.length > 1) {
        val dp = args(0)
        val fs = args(1)
        val dir = new java.io.File(dp)
        if (dir.exists && dir.isDirectory) {
            println("start analysising directory :" + dir.getName)
            val result = loopdir(dir, 0, fs)
            println("line number :" + result)
        } else 
            println("error input " + dp)
    } else
        println("please input argeuemnts: target directory and file suffix")

    def loopdir(dir: File, init: Int, fs:String): Int = {
        println("loop directory " + dir.getName)
        var sum = init
        for (file <- dir.listFiles.toIterator if file.isDirectory)
            sum = sum + loopdir(file, init, fs)
        for (file <- dir.listFiles.toIterator if file.isFile && file.getName.endsWith("." + fs)) {
            val n = countlines(file)
            sum = sum + n
        }
        sum
    }

    def countlines(src: File): Int = {
        print("analysising file " + src.getName)
        val source = Source.fromFile(src, "UTF-8")
        val result = source.getLines().length    
        source.close()
        println(", line number " + result)
        result
    }
}
