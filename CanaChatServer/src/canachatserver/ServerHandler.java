/*
 * GNU License.
 */
package canachatserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Map;
import java.net.Socket;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.translate.Translate;
import com.google.api.services.translate.model.TranslationsListResponse;
import com.google.api.services.translate.model.TranslationsResource;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import java.util.AbstractMap;

/**
 * A handler thread class. Handlers are spawned from the listening loop and are
 * responsible for a dealing with a single client and broadcasting its messages.
 *
 * When a client connects the server requests a screen name by sending the
 * client the text "SUBMITNAME", and keeps requesting a name until a unique one
 * is received. After a client submits a unique name, the server acknowledges
 * with "NAMEACCEPTED". Then all messages from that client will be broadcast to
 * all other clients that have submitted a unique screen name. The broadcast
 * messages are prefixed with "MESSAGE ".
 *
 * @author Breno Viana
 * @version 24/04/2017
 */
public class ServerHandler extends Thread {

    // Receive client messages
    private BufferedReader in;
    // Send client messages
    private PrintWriter out;

    // Client name
    private String name;
    // Client language
    private String language;

    // Socket
    private Socket socket;

    /**
     * The set of all names of clients in the chat room. Maintained so that we
     * can check that new clients are not registering name already in use.
     */
    private static HashMap<String, String> names = new HashMap<String, String>();

    /**
     * The set of all the print writers for all the clients. This set is kept so
     * we can easily broadcast messages.
     */
    private static HashMap<PrintWriter, HashMap.Entry<String, String>> writers
            = new HashMap<PrintWriter, HashMap.Entry<String, String>>();

    /**
     * Constructs a handler thread, squirreling away the socket. All the
     * interesting work is done in the run method.
     *
     * @param socket Server socket
     */
    public ServerHandler(Socket socket) {
        this.socket = socket;
    }

    /**
     * Services this thread's client by repeatedly requesting a screen name
     * until a unique one has been submitted, then acknowledges the name and
     * registers the output stream for the client in a global set, then
     * repeatedly gets inputs and broadcasts them.
     */
    @Override
    public void run() {
        try {
            // Create character streams for the socket.
            this.in = new BufferedReader(new InputStreamReader(
                    this.socket.getInputStream()));
            this.out = new PrintWriter(this.socket.getOutputStream(), true);

            // Request a name from this client.  Keep requesting until
            // a name is submitted that is not already used.  Note that
            // checking for the existence of a name and adding the name
            // must be done while locking the set of names.
            while (true) {
                // Get name and language of the client
                this.out.println("SUBMITNAME");
                this.name = this.in.readLine();
                this.language = this.in.readLine();
                // Check if is a valid value
                if (this.name == null) {
                    return;
                }
                // Adds the client to chat
                synchronized (this.names) {
                    if (!this.names.values().contains(this.name)) {
                        this.names.put(this.name, this.language);
                        break;
                    }
                }
            }

            // Now that a successful name has been chosen, add the
            // socket's print writer to the set of all writers so
            // this client can receive broadcast messages.
            this.out.println("NAMEACCEPTED");
            this.writers.put(this.out,
                    (new AbstractMap.SimpleEntry<String, String>(this.name,
                            this.language)));

            // Accept messages from this client and broadcast them.
            // Ignore other clients that cannot be broadcasted to.
            while (true) {
                // Get message
                String input = this.in.readLine();
                // Check if the message is valid
                if (input == null) {
                    return;
                }
                // Send message to all chat clients and translate to respective
                // language
                for (Map.Entry<PrintWriter, HashMap.Entry<String, String>> writer
                        : this.writers.entrySet()) {
                    // Check 
                    if (writer.getValue().getKey().equals(this.name)) {
                        continue;
                    }
                    // Checks if the language of this client is the same as the
                    // client that sent the message
                    if (!writer.getValue().getValue().equals(this.language)) {
                        try {
                            // Initialize the translator
                            Translate t = new Translate.Builder(
                                    GoogleNetHttpTransport.newTrustedTransport(),
                                    GsonFactory.getDefaultInstance(), null)
                                    // Need to update this to your App-Name
                                    .setApplicationName("CanaChat")
                                    .build();
                            // Prepare to translate
                            Translate.Translations.List list = t.new Translations().list(
                                    Arrays.asList(input),
                                    //Target language
                                    writer.getValue().getValue());
                            System.out.println(writer.getValue());
                            // Google Cloud API
                            list.setKey(APIKEY.GOOGLE_APPLICATION_CREDENTIALS);
                            // Translate message
                            TranslationsListResponse response = list.execute();
                            // Send messages
                            for (TranslationsResource tr : response.getTranslations()) {
                                writer.getKey().println("MESSAGE " + this.name + ": " + tr.getTranslatedText());
                                System.out.println("Sent by: " + this.name);
                                System.out.println("Message: " + tr.getTranslatedText());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            writer.getKey().println("MESSAGE " + this.name + ": " + input);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            // This client is going down!  Remove its name and its print
            // writer from the sets, and close its socket.
            if (this.name != null) {
                this.names.remove(this.name);
            }
            if (this.out != null) {
                this.writers.remove(this.out);
            }
            try {
                this.socket.close();
            } catch (IOException e) {
            }
        }
    }
}