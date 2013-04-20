package com.daveayan.mirage;

import org.junit.Test;

import sample.domain.Account;

public class SetFieldInObject_Test {
	@Test
	public void test() {
		Account account = (Account) ReflectionUtils.getInstanceOfClassForcibly(Account.class);
		ReflectionUtils.setFieldInObject(account, "accountNumber", 123322);
		System.out.println(account);
	}
}
