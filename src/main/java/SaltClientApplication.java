import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

void main() throws IOException, URISyntaxException, InterruptedException {
  var content = "";
  var httpClient = HttpClient.newHttpClient();

  while (true) {
    Thread.sleep(100); // add small pause

    var currentContent = readClipboard();

    if (!currentContent.equals(content) && currentContent.startsWith("salt:")) {
      content = currentContent.replace("salt:", "");

      var output = translate(content, httpClient);
      writeClipboard(output);
      launchNotification();
    }
  }
}

private static String translate(String content, HttpClient httpClient) throws URISyntaxException, IOException, InterruptedException {
  String body = String.format("""
          {
            "mode": "spa-cat_valencia",
            "data": "%s",
            "marks": false
          }
          """, content);

  var request = HttpRequest
          .newBuilder()
          .uri(new URI("https://innovacion-mov.gva.es/pai_bus_inno/SALT/SaltService_REST_v2_00/api/translate"))
          .header("Authorization", "Basic c2FsdHVzdTpwd2RwYWkx")
          .header("x-api-key", "eddd4e6820ff6e4d3f033cc0bcd63f45")
          .header("aplicacion", "SALT")
          .POST(HttpRequest.BodyPublishers.ofString(body)
          ).build();

  var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

  var output = response.body().substring(9, response.body().length() - 2); // output: {data: "hola món"}
  return output;
}

/**
 * @return Content from clipboard
 */
String readClipboard() {
  try {
    var clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    var transferable = clipboard.getContents(null);

    if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
      return transferable.getTransferData(DataFlavor.stringFlavor).toString();
    } else {
      return "";
    }
  } catch (UnsupportedFlavorException | IOException ex) {
    return "";
  }
}

/**
 * @param input to add in the clipboard
 */
void writeClipboard(String input) {
  var clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
  var data = new StringSelection(input);
  clipboard.setContents(data, data);
}

/**
 * Native notification system from linux, adapt if you are using other OS
 *
 * @throws IOException
 */
void launchNotification() throws IOException {
  ProcessBuilder builder = new ProcessBuilder(
          "zenity",
          "--notification",
          "--text=Salt client\\nTraducción realizada en tu portapapeles");
  builder.inheritIO().start();
}