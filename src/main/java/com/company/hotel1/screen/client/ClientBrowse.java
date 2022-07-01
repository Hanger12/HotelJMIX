package com.company.hotel1.screen.client;

import io.jmix.ui.screen.*;
import com.company.hotel1.entity.Client;

@UiController("Client.browse")
@UiDescriptor("client-browse.xml")
@LookupComponent("clientsTable")
public class ClientBrowse extends StandardLookup<Client> {
}