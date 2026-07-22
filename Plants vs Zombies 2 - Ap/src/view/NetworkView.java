package view;

import controllers.menus.SecondaryMenus.Network;
import models.utils.RegexHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetworkView extends View {

    public NetworkView() {
        menu = new Network();
    }

    @Override
    public void input() {
        menu.ShowCurrentMenu();
        super.input();

        if (handleGlobalCommands(input)) {
            return;
        }

        if (input.matches("(?i)^menu\\s+exit$")) {
            menu.exitMenu();
            return;
        }

        Matcher connectMatcher = Pattern.compile(RegexHelper.NETWORK_CONNECT).matcher(input);
        Matcher hostMatcher = Pattern.compile(RegexHelper.NETWORK_HOST).matcher(input);

        Network networkMenu = (Network) menu;

        if (connectMatcher.matches()) {
            String ip = connectMatcher.group("ip");
            int port = Integer.parseInt(connectMatcher.group("port"));
            networkMenu.connectToServer(ip, port);
        }
        else if (hostMatcher.matches()) {
            int port = Integer.parseInt(hostMatcher.group("port"));
            networkMenu.hostServer(port);
        }
        else {
            System.out.println("Invalid command!");
        }
    }
}