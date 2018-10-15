package com.userfront.userfront.service.UserServiceImpl;

import com.userfront.userfront.dao.PrimaryAccountDao;
import com.userfront.userfront.dao.SavingsAccountDao;
import com.userfront.userfront.domain.*;
import com.userfront.userfront.service.AccountService;
import com.userfront.userfront.service.TransactionService;
import com.userfront.userfront.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
@Slf4j
@Component
public class AccountServiceImpl implements AccountService {

    private static int nextAccountNumber=11223344;

    @Autowired
    private PrimaryAccountDao primaryAccountDao;

    @Autowired
    private SavingsAccountDao savingsAccountDao;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;


    @Override
    public PrimaryAccount createPrimaryAccount() {
        PrimaryAccount primaryAccount =new  PrimaryAccount();
        primaryAccount.setAccountBalance( new BigDecimal(0.0));
        primaryAccount.setAccountNumber(accountGen());

        primaryAccountDao.save(primaryAccount);

        return primaryAccountDao.findByAccountNumber(primaryAccount.getAccountNumber());
    }

    @Override
    public SavingsAccount createSavingsAccount() {
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountBalance(new BigDecimal(0.0));
        savingsAccount.setAccountNumber(accountGen());

        savingsAccountDao.save(savingsAccount);

        return savingsAccountDao.findByAccountNumber(savingsAccount.getAccountNumber());
    }

    @Override
    public void deposit(String accountType, double amount, Principal principal) {

        User user = userService.findByUsername(principal.getName());

        if(accountType.equalsIgnoreCase("Primary")){

            PrimaryAccount primaryAccount = user.getPrimaryAccount();
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(amount)));

            primaryAccountDao.save(primaryAccount);

            Date date = new Date();

            PrimaryTransaction primaryTransaction = new PrimaryTransaction(date,"Deposit to Primary Account","Account","Finished",amount,primaryAccount.getAccountBalance(),primaryAccount);

            //primaryTransactionDao.save(primaryTransaction);

            log.info("transaction-list {}",primaryTransaction.getPrimaryAccount().getId());
            transactionService.savePrimaryDepositTransaction(primaryTransaction);


        }else if(accountType.equalsIgnoreCase("Savings")){
            SavingsAccount savingsAccount = user.getSavingsAccount();
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
            savingsAccountDao.save(savingsAccount);

            Date date   = new Date();
            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Deposit to savings Account","Account","Finished",amount,savingsAccount.getAccountBalance(),savingsAccount);
            //savingsTransactionDao.save(savingsTransaction);
            transactionService.saveSavingsDepositTransaction(savingsTransaction);
        }

    }

    @Override
    public void withdraw(String accountType, Double amount, Principal principal) {
        User user = userService.findByUsername(principal.getName());

        if(accountType.equalsIgnoreCase("Primary")){
            PrimaryAccount primaryAccount = user.getPrimaryAccount();
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            primaryAccountDao.save(primaryAccount);

            Date date = new Date();

            PrimaryTransaction primaryTransaction = new PrimaryTransaction(date,"Withdraw from Primary Account","Account","Finished",amount,primaryAccount.getAccountBalance(),primaryAccount);

            transactionService.savePrimaryWithdrawTransaction(primaryTransaction);
        }else if(accountType.equalsIgnoreCase("Savings")){
            SavingsAccount savingsAccount = user.getSavingsAccount();
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            savingsAccountDao.save(savingsAccount);

            Date date = new Date();

            SavingsTransaction savingsTransaction = new SavingsTransaction(date,"Withdraw from Savings Account","Account","Finished",amount,savingsAccount.getAccountBalance(),savingsAccount);
            transactionService.saveSavingsWithdrawTransaction(savingsTransaction);
        }


    }

    private int accountGen(){
        return ++nextAccountNumber;
    }
}
