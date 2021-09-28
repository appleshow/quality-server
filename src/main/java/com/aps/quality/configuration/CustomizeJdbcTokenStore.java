/*
 *Copyright Robert Bosch GmbH. All rights reserved, also regarding any disposal, exploration, reproduction, editing,
 *distribution, as well as in the event of applications for industrial property rights.
 */
package com.aps.quality.configuration;

import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

public class CustomizeJdbcTokenStore extends JdbcTokenStore {

    public CustomizeJdbcTokenStore(DataSource dataSource) {
        super(dataSource);
    }
}
