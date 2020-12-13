package com.lys.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class CommandHelper
{
	static class PrintStream extends Thread
	{
		InputStream inputStream = null;
		boolean isError = false;
		BufferedReader bufferedReader = null;
		StringBuffer stringBuffer = new StringBuffer();

		public PrintStream(InputStream inputStream, boolean isError)
		{
			this.inputStream = inputStream;
			this.isError = isError;
		}

		@Override
		public void run()
		{
			try
			{
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				String line = null;
				while ((line = bufferedReader.readLine()) != null)
				{
					if (isError)
						LOG.e(" --> " + line);
					else
						LOG.v(" --> " + line);
					stringBuffer.append(line + "\r\n");
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if (null != bufferedReader)
					{
						bufferedReader.close();
					}
					if (null != inputStream)
					{
						inputStream.close();
					}
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	private static class ProcessKiller extends Thread
	{
		private Process process;

		public ProcessKiller(Process process)
		{
			this.process = process;
		}

		@Override
		public void run()
		{
			this.process.destroy();
		}
	}

	public static String executeCommand(String commond)
	{
		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		try
		{
			String cmdStr = Arrays.toString(commond.split(" ")).replace(",", "");
			LOG.v(cmdStr);

			ProcessBuilder builder = new ProcessBuilder();
			builder.command(Arrays.asList(commond.split(" ")));
			process = builder.start();

			PrintStream errorStream = new PrintStream(process.getErrorStream(), true);
			PrintStream inputStream = new PrintStream(process.getInputStream(), false);
			errorStream.start();
			inputStream.start();
			process.waitFor();
			errorStream.join();
			inputStream.join();

			String result = errorStream.stringBuffer.append(inputStream.stringBuffer).toString();

			return result;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		finally
		{
			if (null != process)
			{
				ProcessKiller killer = new ProcessKiller(process);
				runtime.addShutdownHook(killer);
			}
		}
	}

//	public static void main(String[] args)
//	{
//		executeCommand("java -version");
//		LOG.v("-------------------------------- over --------------------------------");
//	}

}
