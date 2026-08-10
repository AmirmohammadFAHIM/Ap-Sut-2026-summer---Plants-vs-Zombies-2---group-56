package view;

import controllers.menus.secondarymenus.Network;
import models.utils.RegexHelper;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetworkView extends View {
    public NetworkView() { menu = new Network(); }

    @Override
    public void input() {
        System.out.println(menu.ShowCurrentMenu());
        super.input();
        if (handleGlobalCommands(input)) return;

        Matcher connectMatcher = Pattern.compile(RegexHelper.NETWORK_CONNECT).matcher(input);
        Matcher hostMatcher = Pattern.compile(RegexHelper.NETWORK_HOST).matcher(input);

        if (connectMatcher.matches()) {
            System.out.println(((Network) menu).connectToServer(connectMatcher.group("ip"), Integer.parseInt(connectMatcher.group("port"))));
        } else if (hostMatcher.matches()) {
            System.out.println(((Network) menu).hostServer(Integer.parseInt(hostMatcher.group("port"))));
        } else {
            System.out.println("Invalid command!");
        }
    }
}