package com.company.hotel1.screen.client;

import com.company.hotel1.app.RegistrationCardCount;
import com.company.hotel1.entity.RegistrationCards;
import io.jmix.core.entity.KeyValueEntity;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import com.company.hotel1.entity.Client;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@UiController("Client.browse")
@UiDescriptor("client-browse.xml")
@LookupComponent("clientsTable")
public class ClientBrowse extends StandardLookup<Client> {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ClientBrowse.class);
    @Autowired
    RegistrationCardCount registrationCardCount;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
       for (KeyValueEntity k:registrationCardCount.loadSorted()){
           log.info(k.getValue("listcount").toString());
       }
    }

    @Subscribe(id = "clientsDl", target = Target.DATA_LOADER)
    public void onClientsDlPreLoad(CollectionLoader.PreLoadEvent<Client> event) {

    }


    



}