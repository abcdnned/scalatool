import javax.mail.internet.MimeMessage
import javax.mail.{Folder, MessagingException, NoSuchProviderException, Session}
import java.util.Properties
 
object EmailHelper extends App {
  val host = "SUI.netis.com.cn"
  val user = "tom.yang"
  val password = "yzj_184"
  val props = new Properties()
  val inboxName = "收件箱"
  props.put("mail.smtp.host", host)
  props.put("mail.imap.starttls.enable", "true");
  props.put("mail.smtp.starttls.enable", "true");
  props.put("mail.smtp.auth", "true");
  val session = Session.getDefaultInstance(props)
  val store = session.getStore("imap")
  println("start fetching")
  try {
    println("try connecting")
    store.connect(host, 587, user, password)
  } catch {
    case e @ (_:NoSuchProviderException|_:MessagingException) => println(e.getMessage)
  }

  try {
    val inbox = store.getFolder(inboxName)
    inbox.open(Folder.READ_ONLY)
    println("ready")
    inbox.getMessages(0, 10) map {
      case message:MimeMessage => println(message.getSubject)
      case _ => println("nothing")
    }
    inbox.close(true)
  } catch {
    case e @ (_:NoSuchProviderException|_:MessagingException) => println(e.getMessage)
  } finally {
    store.close()
  }

  println("finish")
}
