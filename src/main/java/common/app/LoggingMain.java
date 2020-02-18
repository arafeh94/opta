/*
 * Copyright 2010 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package common.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingMain {
    private static LoggingMain instance = new LoggingMain();

    private final transient Logger logger = LoggerFactory.getLogger(getClass());

    public static void log(Object... objects) {
        for (Object object : objects) {
            if (object == null) {
                instance.logger.debug("null");
            } else {
                instance.logger.debug(object.toString());
            }
        }
    }
}