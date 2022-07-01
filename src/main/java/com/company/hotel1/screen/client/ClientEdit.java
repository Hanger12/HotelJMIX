package com.company.hotel1.screen.client;

import io.jmix.ui.screen.*;
import com.company.hotel1.entity.Client;

@UiController("Client.edit")
@UiDescriptor("client-edit.xml")
@EditedEntityContainer("clientDc")
public class ClientEdit extends StandardEditor<Client> {
}