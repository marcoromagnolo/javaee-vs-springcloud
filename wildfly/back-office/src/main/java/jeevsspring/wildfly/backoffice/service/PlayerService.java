package jeevsspring.wildfly.backoffice.service;

import jeevsspring.wildfly.backoffice.dao.AccountDAO;
import jeevsspring.wildfly.backoffice.dao.PlayerDAO;
import jeevsspring.wildfly.backoffice.dao.WalletDAO;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Marco Romagnolo
 */
@Singleton
public class PlayerService {

    @Inject
    private WalletDAO walletDAO;

    @Inject
    private PlayerDAO playerDAO;

    @Inject
    private AccountDAO accountDAO;

    public void wallet() throws ServiceException {

    }

    public void account() throws ServiceException {

    }

    public void login() throws ServiceException {

    }

    public void logout() throws ServiceException {

    }
}
