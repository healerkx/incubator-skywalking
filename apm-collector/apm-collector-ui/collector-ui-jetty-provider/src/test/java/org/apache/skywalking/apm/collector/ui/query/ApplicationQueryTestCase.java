/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.apache.skywalking.apm.collector.ui.query;

import org.apache.skywalking.apm.collector.storage.ui.common.Duration;
import org.apache.skywalking.apm.collector.storage.ui.common.Step;
import org.apache.skywalking.apm.collector.ui.service.ApplicationService;
import org.apache.skywalking.apm.collector.ui.service.ApplicationTopologyService;
import org.apache.skywalking.apm.collector.ui.service.ServerService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import java.text.ParseException;

/**
 * @author peng-yongsheng
 */
public class ApplicationQueryTestCase {

    @Test
    public void testGetAllApplicationByMonthDuration() throws ParseException {
        ApplicationQuery query = new ApplicationQuery(null);
        ApplicationService applicationService = Mockito.mock(ApplicationService.class);
        Whitebox.setInternalState(query, "applicationService", applicationService);
        Mockito.when(applicationService.getApplications(Mockito.anyLong(), Mockito.anyLong())).then(invocation -> {
            Object[] arguments = invocation.getArguments();
            Assert.assertEquals(20170100000000L, arguments[0]);
            Assert.assertEquals(20170199999999L, arguments[1]);
            return null;
        });

        Duration duration = new Duration();
        duration.setStart("2017-01");
        duration.setEnd("2017-01");
        duration.setStep(Step.MONTH);

        query.getAllApplication(duration);
    }

    @Test
    public void testGetAllApplicationByDayDuration() throws ParseException {
        ApplicationQuery query = new ApplicationQuery(null);
        ApplicationService applicationService = Mockito.mock(ApplicationService.class);
        Whitebox.setInternalState(query, "applicationService", applicationService);

        Mockito.when(applicationService.getApplications(Mockito.anyLong(), Mockito.anyLong())).then(invocation -> {
            Object[] arguments = invocation.getArguments();
            Assert.assertEquals(20170101000000L, arguments[0]);
            Assert.assertEquals(20170101999999L, arguments[1]);
            return null;
        });

        Duration duration = new Duration();
        duration.setStart("2017-01-01");
        duration.setEnd("2017-01-01");
        duration.setStep(Step.DAY);

        query.getAllApplication(duration);
    }

    @Test
    public void testGetAllApplicationByHourDuration() throws ParseException {
        ApplicationQuery query = new ApplicationQuery(null);
        ApplicationService applicationService = Mockito.mock(ApplicationService.class);
        Whitebox.setInternalState(query, "applicationService", applicationService);

        Mockito.when(applicationService.getApplications(Mockito.anyLong(), Mockito.anyLong())).then(invocation -> {
            Object[] arguments = invocation.getArguments();
            Assert.assertEquals(20170101010000L, arguments[0]);
            Assert.assertEquals(20170101019999L, arguments[1]);
            return null;
        });

        Duration duration = new Duration();
        duration.setStart("2017-01-01 01");
        duration.setEnd("2017-01-01 01");
        duration.setStep(Step.HOUR);

        query.getAllApplication(duration);
    }

    @Test
    public void testGetAllApplicationByMinuteDuration() throws ParseException {
        ApplicationQuery query = new ApplicationQuery(null);
        ApplicationService applicationService = Mockito.mock(ApplicationService.class);
        Whitebox.setInternalState(query, "applicationService", applicationService);

        Mockito.when(applicationService.getApplications(Mockito.anyLong(), Mockito.anyLong())).then(invocation -> {
            Object[] arguments = invocation.getArguments();
            Assert.assertEquals(20170101010100L, arguments[0]);
            Assert.assertEquals(20170101010199L, arguments[1]);
            return null;
        });

        Duration duration = new Duration();
        duration.setStart("2017-01-01 0101");
        duration.setEnd("2017-01-01 0101");
        duration.setStep(Step.MINUTE);

        query.getAllApplication(duration);
    }

    @Test
    public void testGetAllApplicationBySecondDuration() throws ParseException {
        ApplicationQuery query = new ApplicationQuery(null);
        ApplicationService applicationService = Mockito.mock(ApplicationService.class);
        Whitebox.setInternalState(query, "applicationService", applicationService);

        Mockito.when(applicationService.getApplications(Mockito.anyLong(), Mockito.anyLong())).then(invocation -> {
            Object[] arguments = invocation.getArguments();
            Assert.assertEquals(20170101010101L, arguments[0]);
            Assert.assertEquals(20170101010101L, arguments[1]);
            return null;
        });

        Duration duration = new Duration();
        duration.setStart("2017-01-01 010101");
        duration.setEnd("2017-01-01 010101");
        duration.setStep(Step.SECOND);

        query.getAllApplication(duration);
    }


    @Test
    public void testGetSlowByMonthDuration() throws ParseException {
        ApplicationQuery query = new ApplicationQuery(null);
        ApplicationService applicationService = Mockito.mock(ApplicationService.class);
        Whitebox.setInternalState(query, "applicationService", applicationService);
        Mockito.when(applicationService.getSlowService(
                Mockito.anyInt(), Mockito.anyObject(),
                Mockito.anyLong(), Mockito.anyLong(),
                Mockito.anyLong(), Mockito.anyLong(),
                Mockito.anyInt())
        ).then(invocation -> {
            Object[] arguments = invocation.getArguments();
            Assert.assertEquals(201701L, arguments[2]);
            Assert.assertEquals(201701L, arguments[3]);
            Assert.assertEquals(20170100000000L, arguments[4]);
            Assert.assertEquals(20170199999999L, arguments[5]);
            return null;
        });

        Duration duration = new Duration();
        duration.setStart("2017-01");
        duration.setEnd("2017-01");
        // the step in exchangeToTimeBucket and startTimeDurationToSecondTimeBucket and endTimeDurationToSecondTimeBucket has its own testcase,so here wo give
        // one step is enough.
        duration.setStep(Step.MONTH);

        query.getSlowService(-1, duration, -1);
    }

    @Test
    public void testGetServerThroughput() throws ParseException {
        ApplicationQuery query = new ApplicationQuery(null);
        ServerService serverService = Mockito.mock(ServerService.class);
        Whitebox.setInternalState(query, "serverService", serverService);

        Mockito.when(serverService.getServerThroughput(
                Mockito.anyInt(), Mockito.anyObject(),
                Mockito.anyLong(), Mockito.anyLong(),
                Mockito.anyLong(), Mockito.anyLong(),
                Mockito.anyInt())
        ).then(invocation -> {
            Object[] arguments = invocation.getArguments();
            Assert.assertEquals(201701L, arguments[2]);
            Assert.assertEquals(201702L, arguments[3]);
            Assert.assertEquals(20170100000000L, arguments[4]);
            Assert.assertEquals(20170299999999L, arguments[5]);
            return null;
        });

        Duration duration = new Duration();
        duration.setStart("2017-01");
        duration.setEnd("2017-02");
        duration.setStep(Step.MONTH);

        query.getServerThroughput(-1, duration, -1);
    }

    @Test
    public void testGetApplicationTopology() throws ParseException {
        ApplicationQuery query = new ApplicationQuery(null);
        ApplicationTopologyService applicationTopologyService = Mockito.mock(ApplicationTopologyService.class);
        Whitebox.setInternalState(query, "applicationTopologyService", applicationTopologyService);

        Mockito.when(
                applicationTopologyService.getApplicationTopology(
                        Mockito.anyObject(), Mockito.anyInt(),
                        Mockito.anyLong(), Mockito.anyLong(),
                        Mockito.anyLong(), Mockito.anyLong())
        ).then(invocation -> {
            Object[] arguments = invocation.getArguments();
            Assert.assertEquals(201701L, arguments[2]);
            Assert.assertEquals(201703L, arguments[3]);
            Assert.assertEquals(20170100000000L, arguments[4]);
            Assert.assertEquals(20170399999999L, arguments[5]);
            return null;
        });

        Duration duration = new Duration();
        duration.setStart("2017-01");
        duration.setEnd("2017-03");
        duration.setStep(Step.MONTH);

        query.getApplicationTopology(-1, duration);
    }

}
