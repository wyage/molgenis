package org.molgenis.data.transaction.index;

import javax.annotation.PostConstruct;

import org.molgenis.data.DataService;
import org.molgenis.data.transaction.MolgenisTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IndexTransactionConfig
{
	@Autowired
	private DataService dataService;

	@Autowired
	private MolgenisTransactionManager transactionManager;

	private final String backendName = "PostgreSQL";

	@Bean
	public IndexTransactionLogMetaData indexTransactionLogMetaData()
	{
		return new IndexTransactionLogMetaData(backendName);
	}

	@Bean
	public IndexTransactionLogEntryMetaData indexTransactionLogEntryMetaData()
	{
		return new IndexTransactionLogEntryMetaData(indexTransactionLogMetaData(), backendName);
	}

	@Bean
	public IndexTransactionLogService indexTransactionLogService()
	{
		return new IndexTransactionLogService(dataService, indexTransactionLogMetaData(),
				indexTransactionLogEntryMetaData());
	}

	@PostConstruct
	public void init()
	{
		transactionManager.addTransactionListener(indexTransactionLogService());
	}
}
