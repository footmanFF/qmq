/*
 * Copyright 2018 Qunar, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package qunar.tc.qmq.delay;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

public class ScheduleIndex {

    private static final Interner<String> INTERNER = Interners.newStrongInterner();

    /**
     * 消息主题
     */
    private final String subject;
    /**
     * 延时时间戳
     */
    private final long scheduleTime;
    /**
     * 在scheduler_log文件中的字节偏移量
     */
    private final long offset;
    /**
     * 在scheduler_log文件中消息字节长度
     */
    private final int size;
    private final long sequence;

    public ScheduleIndex(String subject, long scheduleTime, long offset, int size, long sequence) {
        this.subject = INTERNER.intern(subject);
        this.scheduleTime = scheduleTime;
        this.offset = offset;
        this.size = size;
        this.sequence = sequence;
    }

    public long getScheduleTime() {
        return scheduleTime;
    }

    public long getOffset() {
        return offset;
    }

    public int getSize() {
        return size;
    }

    public long getSequence() {
        return sequence;
    }

    public String getSubject() {
        return subject;
    }
}
