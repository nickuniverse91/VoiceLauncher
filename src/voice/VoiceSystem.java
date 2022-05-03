package voice;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import controller.MainController;

import controller.VoiceWindowController;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.jsgf.*;
import edu.cmu.sphinx.jsgf.rule.*;
import edu.cmu.sphinx.util.props.ConfigurationManagerUtils;

public class VoiceSystem {

    // To access list of programs
    private MainController controller;

    private VoiceWindowController voiceWindowController;

    // Logger
    private Logger logger = Logger.getLogger(getClass().getName());

    // Variables
    private String result;

    // Threads
    Thread	speechThread;
    //Thread	resourcesThread;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // LiveRecognizer
    //private LiveSpeechRecognizer recognizer;

    private volatile boolean runThread = false;

    private volatile boolean threadActive = false;

    private volatile boolean systemStarting = false;

    public VoiceSystem(MainController controller, VoiceWindowController voiceWindowController) {
        // Set controller
        this.controller = controller;
        this.voiceWindowController = voiceWindowController;
    }

    protected void startSpeechThread(Configuration configuration) {

        // alive?
        if (speechThread != null && runThread)
            return;

        // initialise
        speechThread = new Thread(() -> {
            threadActive = true;

            try {
                LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
                runThread = true;
                voiceWindowController.changeLabel(true);
                // Start recognition process pruning previously cached data.
                recognizer.startRecognition(true);

                logger.log(Level.INFO, "You can start to speak...\n");
                voiceWindowController.addToTextArea(LocalDateTime.now().format(formatter) + ": You can start to speak...");
                systemStarting = false;
                while (runThread) {
                    /*
                     * This method will return when the end of speech is
                     * reached. Note that the end pointer will determine the end
                     * of speech.
                     */
                    SpeechResult speechResult = recognizer.getResult();
                    if (speechResult != null) {

                        result = speechResult.getHypothesis();
                        System.out.println("You said: [" + result + "]\n");
                        voiceWindowController.addToTextArea(LocalDateTime.now().format(formatter) + ": You said: [" + result + "]");
                        makeDecision(result);
                        // logger.log(Level.INFO, "You said: " + result + "\n")

                    } else {
                        logger.log(Level.INFO, "I can't understand what you said.\n");
                        voiceWindowController.addToTextArea(LocalDateTime.now().format(formatter) + ": I can't understand what you said.");
                    }
                }

                recognizer.closeRecognizer();

            } catch (Exception ex) {
                systemStarting = false;
                logger.log(Level.WARNING, null, ex);
                voiceWindowController.addToTextArea(LocalDateTime.now().format(formatter) + ": Error\n" + ex.getMessage());
            }


            voiceWindowController.changeLabel(false);

            logger.log(Level.INFO, "SpeechThread has exited...");
            voiceWindowController.addToTextArea(LocalDateTime.now().format(formatter) + ": SpeechThread has exited...");
            threadActive = false;

        });

        speechThread.setDaemon(true);
        // Start
        speechThread.start();

    }

    public boolean isThreadActive() {
        return threadActive;
    }

    public boolean isSystemStarting() {
        return systemStarting;
    }

    public void makeDecision(String speech) {
        // remember to check for and remove 'open' from string. This will make it easier if other commands are added
        if (speech.startsWith("open"))
            controller.launch(speech); // Modify to remove open later
    }

    public void startSystem() {
        systemStarting = true;

        // Loading Message
        logger.log(Level.INFO, "Loading...\n");
        voiceWindowController.addToTextArea(LocalDateTime.now().format(formatter) + ": Loading...");

        // Configuration
        Configuration configuration = new Configuration();

        // Load model from the jar
        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");

        // if you want to use LanguageModelPath disable the 3 lines after which
        // are setting a custom grammar->

        // configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin")

        // Grammar
        configuration.setGrammarPath("resource:/grammars");
        configuration.setGrammarName("grammar");
        configuration.setUseGrammar(true);


        // Start the Thread
        //runThread = true;
        startSpeechThread(configuration);

    }
    //Remember to start and stop threads too
    public void closeSystem() {
        voiceWindowController.addToTextArea(LocalDateTime.now().format(formatter) + ": Stopping...");
        runThread = false;
    }

    public void updateGrammar(ArrayList<String> programNames) {


        // Creates grammar
        JSGFRuleGrammar ruleGrammar = new JSGFRuleGrammar("grammar", new JSGFRuleGrammarManager());

        JSGFRuleToken token1 = new JSGFRuleToken("open");
        JSGFRuleName programsRuleName = new JSGFRuleName("programs"); // <name> // can be used as a reference in an Alternative

        JSGFRuleSequence cmdRuleSequence = new JSGFRuleSequence(new LinkedList<JSGFRule>(Collections.EMPTY_LIST));

        cmdRuleSequence.append(token1);
        cmdRuleSequence.append(programsRuleName);

        JSGFRuleAlternatives programsRuleAlternatives = new JSGFRuleAlternatives(new LinkedList<JSGFRule>(Collections.EMPTY_LIST)); // token | token | token | ...

        for (String name : programNames) {
            JSGFRule program = new JSGFRule();
            program.ruleName = name.toLowerCase();
            programsRuleAlternatives.append(program);
        }

        ruleGrammar.setRule("cmd", cmdRuleSequence, true); // name, rule, public or not?

        ruleGrammar.setRule(programsRuleName.getRuleName(), programsRuleAlternatives, false);

        //System.out.println(ruleGrammar);

        // Save to grammar file
        try {
            //File f = new File(getClass().getResource("/MyResource").toExternalForm());
            ruleGrammar.saveJSGF(ConfigurationManagerUtils.resourceToURL("resource:/grammars/grammar.gram"));
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            voiceWindowController.addToTextArea(LocalDateTime.now().format(formatter) + ": Error updating grammar file");
        }

    }

}


