package moss.renamer;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import eu.pb4.placeholders.api.TextParserUtils;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
public class RenameCommand {
    public static boolean matchesRegex(String input, String regex) {
        // Compile the regex pattern
        Pattern pattern = Pattern.compile(regex);
        // Create a Matcher object for the input string
        Matcher matcher = pattern.matcher(input);

        // Check if the pattern matches anywhere in the input string
        return matcher.find();  // Use find() for a partial match, use matches() for a full string match
    }
    public static int setName(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        if (context.getSource().getEntity().isPlayer()) {
            if (context.getSource().getPlayer().getMainHandStack().isEmpty()) {
                context.getSource().sendMessage(Text.literal("You have no item in your hand.").formatted(Formatting.YELLOW));
                return 0;
            }
            String name = context.getArgument("name", String.class);

            if (matchesRegex(name, "<(click|open_url|run_cmd|run_command|suggest_command|cmd|copy_to_clipboard|change_page|url|run_command |copy|page):([^>]+)")){
                context.getSource().getPlayer().sendMessage(Text.literal("Used forbidden tags.").formatted(Formatting.RED));
                return 0;
            }
            if (!(name.length() > 0)) {
                context.getSource().getPlayer().getMainHandStack().removeCustomName();
                return 1;
            }

            MutableText itemNewName = TextParserUtils.formatText(name).copy();
            if (!itemNewName.getStyle().isItalic()) itemNewName = itemNewName.fillStyle(Style.EMPTY.withItalic(false));

            context.getSource().getPlayer().getMainHandStack().setCustomName(itemNewName);
            return 1;
        } else {
            return 0;
        }
    }
}