package com.challenge.sob.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.sob.exception.SobException;
import com.challenge.sob.model.TransactionDetails;
import com.challenge.sob.service.BlockGeneratorService;
import com.challenge.sob.util.BlockGeneratorUtil;

@Service
public class BlockGeneratorServiceImpl implements BlockGeneratorService
{
	@Autowired
	private BlockGeneratorUtil blockGeneratorUtil;

	private static final String WEIGHT_LIMIT = "4000000";
	private static final String OUTPUT_FILENAME = "output_block_file";

	@Override
	public File generateBlock(File csvFile) throws SobException
	{
		File blockFile = null;
		try
		{
			List<TransactionDetails> transactionDetails = blockGeneratorUtil.csvReader(csvFile);

			Collections.sort(transactionDetails, new Comparator<TransactionDetails>() {
				public int compare(TransactionDetails t1, TransactionDetails t2)
				{
					return t2.getFee()-t1.getFee();
				}
			});

			List<String> blockData = new ArrayList<>();
			BigInteger weight = BigInteger.ZERO;
			for(TransactionDetails details :transactionDetails)
			{
				createBlockData(details, weight, blockData, transactionDetails);
			}

			blockFile = new File(OUTPUT_FILENAME+System.currentTimeMillis()+".txt");
			FileWriter fileWriter = new FileWriter(blockFile);
			for(String txData :blockData)
			{
				fileWriter.write(txData);
				fileWriter.write("\r\n");
			}
			fileWriter.close();
		}catch (Exception e) {
			throw new SobException(e.getMessage());
		}
		return blockFile;
	}

	private List<String> createBlockData(TransactionDetails details, BigInteger weight, 
			List<String> blockData, List<TransactionDetails> masterData)
	{
		if(details.getParentTxId() == null || details.getParentTxId().isEmpty())
		{
			if(!blockData.contains(details.getTxId()))
			{
				weight = weight.add(new BigInteger(details.getWeight().toString()));
				if(weight.compareTo(new BigInteger(WEIGHT_LIMIT)) >= 0) {
					return blockData;
				}
				blockData.add(details.getTxId());
			}
		}else
		{
			for(String parentTxId :details.getParentTxId()) //first add parent
			{
				//Only if that is not already present
				if(!blockData.contains(parentTxId))
				{
					Optional<TransactionDetails> parentTransaction = masterData.stream()
							.filter(p -> p.getTxId().equals(parentTxId))
							.findAny();
					if(parentTransaction.isPresent())
					{
						createBlockData(parentTransaction.get(), weight, blockData, masterData);
					}
				}
			}
			if(!blockData.contains(details.getTxId()))
			{
				weight = weight.add(new BigInteger(details.getWeight().toString()));
				if(weight.compareTo(new BigInteger(WEIGHT_LIMIT)) >= 0) {
					return blockData;
				}
				blockData.add(details.getTxId());
			}
		}
		return blockData;	
	}
}
