package com.userfront.userfront.service;

import com.userfront.userfront.domain.PrimaryAccount;
import com.userfront.userfront.domain.SavingsAccount;

import java.security.Principal;

public interface AccountService {


   PrimaryAccount createPrimaryAccount();

   SavingsAccount createSavingsAccount();

   void deposit(String accountType, double amount, Principal principal);

   void withdraw(String  accountType, Double amount,Principal principal);


}
