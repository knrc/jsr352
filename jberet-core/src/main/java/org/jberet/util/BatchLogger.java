/*
 * Copyright (c) 2012-2013 Red Hat, Inc. and/or its affiliates.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Cheng Fang - Initial API and implementation
 */

package org.jberet.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import javax.batch.operations.BatchRuntimeException;
import javax.batch.operations.JobExecutionAlreadyCompleteException;
import javax.batch.operations.JobExecutionIsRunningException;
import javax.batch.operations.JobExecutionNotMostRecentException;
import javax.batch.operations.JobExecutionNotRunningException;
import javax.batch.operations.JobRestartException;
import javax.batch.operations.JobStartException;
import javax.batch.operations.NoSuchJobException;
import javax.batch.operations.NoSuchJobExecutionException;
import javax.batch.runtime.BatchStatus;
import javax.xml.stream.Location;

import org.jberet.runtime.context.AbstractContext;
import org.jboss.logging.BasicLogger;
import org.jboss.logging.Logger;
import org.jboss.logging.annotations.Cause;
import org.jboss.logging.annotations.LogMessage;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageLogger;

@MessageLogger(projectCode = "jberet")
public interface BatchLogger extends BasicLogger {
    BatchLogger LOGGER = org.jboss.logging.Logger.getMessageLogger(BatchLogger.class, BatchLogger.class.getPackage().getName());

    @Message(id = 1, value = "Failed to create artifact with ref name %s.  Ensure CDI beans.xml is present and batch.xml, if any, is configured properly.")
    IllegalStateException failToCreateArtifact(@Cause Throwable e, String ref);

    @LogMessage(level = Logger.Level.ERROR)
    @Message(id = 2,
            value = "Usage: java -classpath ... -Dkey1=val1 ... org.jberet.Main jobXML%nThe following application args are invalid:%n%s")
    void mainUsage(List<String> args);

    @LogMessage(level = Logger.Level.WARN)
    @Message(id = 3, value = "Failed to run batchlet %s")
    void failToRunBatchlet(@Cause Throwable e, Object o);

    @Message(id = 4, value = "Failed to get job xml file for job %s")
    JobStartException failToGetJobXml(@Cause Throwable e, String jobName);

    @Message(id = 5, value = "Failed to parse and bind XML for job %s")
    JobStartException failToParseJobXml(@Cause Throwable e, String jobName);

    @Message(id = 6, value = "Failed to parse batch XML %s")
    JobStartException failToParseBatchXml(@Cause Throwable e, String batchXML);

    @Message(id = 7, value = "Failed to process batch application metadata for job %s")
    JobStartException failToProcessMetaData(@Cause Throwable e, String jobName);

    @LogMessage(level = Logger.Level.WARN)
    @Message(id = 8, value = "Failed to write batch artifact xml file")
    void failToWriteBatchXml(@Cause Throwable e);

    @LogMessage(level = Logger.Level.WARN)
    @Message(id = 9, value = "Failed to identify batch artifact")
    void failToIdentifyArtifact(@Cause Throwable e);

    @Message(id = 10, value = "A step cannot contain both chunk type and batchlet type: %s")
    @LogMessage(level = Logger.Level.WARN)
    void cannotContainBothChunkAndBatchlet(String stepId);

    @Message(id = 11, value = "A concrete step must contain either a chunk or batchlet type: %s")
    @LogMessage(level = Logger.Level.WARN)
    void stepContainsNoChunkOrBatchlet(String stepId);

    @LogMessage(level = Logger.Level.INFO)
    @Message(id = 12, value = "Submitted batchlet task %s in thread %s")
    void submittedBatchletTask(String b, Thread th);

    @Message(id = 13, value = "No method matches the annotation %s in artifact %s")
    IllegalStateException noMethodMatchingAnnotation(Class<? extends Annotation> ann, Object artifact);

    @Message(id = 14, value = "No job execution with id %s")
    NoSuchJobExecutionException noSuchJobExecution(long executionId);

    @Message(id = 15, value = "Unrecognized property category: %s, variable name: %s in property value: %s")
    @LogMessage(level = Logger.Level.WARN)
    void unrecognizedPropertyReference(String category, String variableName, String propVal);

    @Message(id = 16, value = "Invalid exception filter class '%s'")
    @LogMessage(level = Logger.Level.WARN)
    void invalidExceptionClassFilter(@Cause Throwable cause, String cls);

    @Message(id = 17, value = "The job: %s already exists in the job repository and will not be added.")
    @LogMessage(level = Logger.Level.TRACE)
    void jobAlreadyExists(String jobId);

    @Message(id = 18, value = "Failed to run job %s, %s, %s")
    @LogMessage(level = Logger.Level.ERROR)
    void failToRunJob(@Cause Throwable e, String jobName, String stepName, Object artifact);

    @Message(id = 19, value = "Unrecognizable job element: %s in job: %s")
    IllegalStateException unrecognizableJobElement(String jobElementName, String jobName);

    @Message(id = 20, value = "Cycle detected in property reference: %s")
    BatchRuntimeException cycleInPropertyReference(List<String> referringExpressions);

    @Message(id = 21, value = "Possible syntax errors in property: %s")
    @LogMessage(level = Logger.Level.WARN)
    void possibleSyntaxErrorInProperty(String prop);

    @Message(id = 22, value = "The step %s would form a loopback in sequence: %s")
    IllegalStateException loopbackStep(String stepId, String executedSteps);

    @Message(id = 23, value = "The requested batch operation %s is not supported in %s")
    IllegalStateException batchOperationNotSupported(String op, AbstractContext context);

    @Message(id = 24, value = "Cycles detected in job element inheritance: %s")
    JobStartException cycleInheritance(String inheritingElements);

    @Message(id = 25, value = "Job execution %s is running and cannot be abandoned.")
    JobExecutionIsRunningException jobExecutionIsRunningException(long jobExecutionId);

    @Message(id = 26, value = "Job execution %s has already completed and cannot be restarted.")
    JobExecutionAlreadyCompleteException jobExecutionAlreadyCompleteException(long jobExecutionId);

    @Message(id = 27, value = "Failed to restart job execution %s, which had batch status %s.")
    JobRestartException jobRestartException(long jobExecutionId, BatchStatus previousStatus);

    @Message(id = 28, value = "Job execution %s is not the most recent execution of job instance %s.")
    JobExecutionNotMostRecentException jobExecutionNotMostRecentException(long jobExecutionId, long jobInstanceId);

    @Message(id = 29, value = "Job execution %s has batch status %s, and is not running.")
    JobExecutionNotRunningException jobExecutionNotRunningException(long jobExecutionId, BatchStatus batchStatus);

    @Message(id = 30, value = "A decision cannot be the first element: %s")
    @LogMessage(level = Logger.Level.WARN)
    void decisionCannotBeFirst(String decisionId);

    @Message(id = 31, value = "Could not resolve expression because: %s")
    @LogMessage(level = Logger.Level.DEBUG)
    void unresolvableExpression(String message);

    @Message(id = 32, value = "Could not resolve the expression: %s")
    BatchRuntimeException unresolvableExpressionException(String expression);

    @Message(id = 33, value = "The step %s has started %s times and reached its start limit %s")
    BatchRuntimeException stepReachedStartLimit(String stepName, int startLimit, int startCount);

    @Message(id = 34, value = "Invalid chunk checkpoint-policy %s.  It must be either item or custom.")
    BatchRuntimeException invalidCheckpointPolicy(String checkpointPolicy);

    @Message(id = 35, value = "Invalid chunk item-count %s.  It must be greater than 0.")
    BatchRuntimeException invalidItemCount(int itemCount);

    @Message(id = 36, value = "checkpoint-algorithm element is missing in step %s.  It is required for custom checkpoint-policy.")
    BatchRuntimeException checkpointAlgorithmMissing(String stepName);

    @Message(id = 37, value = "The specified job with the name %s does not exist.")
    NoSuchJobException noSuchJobException(String jobName);

    @Message(id = 38, value = "Failed to stop the job %s, %s, %s")
    @LogMessage(level = Logger.Level.WARN)
    void failToStopJob(@Cause Throwable cause, String jobName, String stepName, Object additionalInfo);

    @Message(id = 39, value = "Failed to clone %s when running job [%s] and step [%s]")
    @LogMessage(level = Logger.Level.WARN)
    void failToClone(@Cause Throwable cause, Object original, String jobName, String stepName);

    @Message(id = 40, value = "Failed to inject value %s into field %s, because the field type %s is not supported for property injection.")
    BatchRuntimeException unsupportedFieldType(String v, Field f, Class<?> t);

    @Message(id = 41, value = "Failed to inject value %s into field %s")
    BatchRuntimeException failToInjectProperty(@Cause Throwable th, String v, Field f);

    @Message(id = 42, value = "Failed to destroy artifact %s")
    @LogMessage(level = Logger.Level.WARN)
    void failToDestroyArtifact(@Cause Throwable cause, Object artifact);

    @Message(id = 43, value = "The configuration file %s is not found in the classpath, and will use the default configuration.")
    @LogMessage(level = Logger.Level.TRACE)
    void useDefaultJBeretConfig(String configFile);

    @Message(id = 44, value = "Failed to load configuration file %s")
    BatchRuntimeException failToLoadConfig(@Cause Throwable th, String configFile);

    @Message(id = 45, value = "Unrecognized job repository type %s")
    BatchRuntimeException unrecognizedJobRepositoryType(String v);

    @Message(id = 46, value = "Failed to look up datasource %s")
    BatchRuntimeException failToLookupDataSource(@Cause Throwable cause, String dataSourceName);

    @Message(id = 47, value = "Failed to obtain connection from %s, %s")
    BatchRuntimeException failToObtainConnection(@Cause Throwable cause, Object connectionSource, Object props);

    @Message(id = 48, value = "Failed to load sql properties %s")
    BatchRuntimeException failToLoadSqlProperties(@Cause Throwable cause, String sqlFile);

    @Message(id = 49, value = "Tables created for batch job repository with DDL file %s")
    @LogMessage(level = Logger.Level.INFO)
    void tableCreated(String ddlFile);

    @Message(id = 50, value = "Tables created for batch job repository with DDL content:%n %s")
    @LogMessage(level = Logger.Level.INFO)
    void tableCreated2(String ddlContent);

    @Message(id = 51, value = "Failed to create tables for batch job repository with DDL %s%n%s")
    BatchRuntimeException failToCreateTables(@Cause Throwable cause, String ddlFile, String ddlContent);

    @Message(id = 52, value = "Failed to load ddl file %s")
    BatchRuntimeException failToLoadDDL(String ddlFile);

    @Message(id = 53, value = "Failed to run %s")
    BatchRuntimeException failToRunQuery(@Cause Throwable cause, String sql);

    @Message(id = 54, value = "Failed to close %s: %s")
    @LogMessage(level = Logger.Level.WARN)
    void failToClose(@Cause Throwable cause, Class<?> resourceType, Object obj);

    @Message(id = 55, value = "Persisted %s with id %s")
    @LogMessage(level = Logger.Level.INFO)
    void persisted(Object obj, long id);

    @Message(id = 56, value = "Unexpected XML element '%s' at location %s")
    BatchRuntimeException unexpectedXmlElement(String element, Location location);

    @Message(id = 57, value = "Failed to get XML attribute '%s' at location %s")
    BatchRuntimeException failToGetAttribute(String attributeName, Location location);

    @Message(id = 58, value = "Cannot have both next attribute and next element at location %s  Next attribute is already set to %s")
    BatchRuntimeException cannotHaveBothNextAttributeAndElement(Location location, String nextAttributeValue);

    @Message(id = 59, value = "The job instance: %s already exists in the job repository and cannot be added again.")
    BatchRuntimeException jobInstanceAlreadyExists(long jobInstanceId);

    @Message(id = 60, value = "The job execution: %s already exists in the job repository and cannot be added again.")
    BatchRuntimeException jobExecutionAlreadyExists(long jobExecutionId);
}