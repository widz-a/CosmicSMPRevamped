package withicality.csmp.commands.utils;

import com.Zrips.CMI.utils.CMIEncoder;
import withicality.csmp.CosmicCommand;

public class CMIDecode extends CosmicCommand {

    public CMIDecode() {
        super("cmidecode", "a", "a", "debug.cmidecode");
        setMinArguments(1);
    }

    @Override
    protected void onCommand() {
        String str = String.join(" ", args);
        if (!CMIEncoder.isEncoded(str)) {
            sender.sendMessage("It isnt encoded");
        }
        String decoded = CMIEncoder.decode(str);
        sender.sendMessage(decoded == null ? "null" : decoded);
    }
}
