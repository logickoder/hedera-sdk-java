/*-
 *
 * Hedera Java SDK
 *
 * Copyright (C) 2020 - 2024 Hedera Hashgraph, LLC
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
 *
 */
package com.hedera.hashgraph.sdk;

/**
 * Enum for the freeze types.
 */
public enum FreezeType {
    /**
     * An (invalid) default value for this enum, to ensure the client explicitly sets
     * the intended type of freeze transaction.
     */
    UNKNOWN_FREEZE_TYPE(com.hedera.hashgraph.sdk.proto.FreezeType.UNKNOWN_FREEZE_TYPE),

    /**
     * Freezes the network at the specified time. The start_time field must be provided and
     * must reference a future time. Any values specified for the update_file and file_hash
     * fields will be ignored. This transaction does not perform any network changes or
     * upgrades and requires manual intervention to restart the network.
     */
    FREEZE_ONLY(com.hedera.hashgraph.sdk.proto.FreezeType.FREEZE_ONLY),

    /**
     * A non-freezing operation that initiates network wide preparation in advance of a
     * scheduled freeze upgrade. The update_file and file_hash fields must be provided and
     * valid. The start_time field may be omitted and any value present will be ignored.
     */
    PREPARE_UPGRADE(com.hedera.hashgraph.sdk.proto.FreezeType.PREPARE_UPGRADE),

    /**
     * Freezes the network at the specified time and performs the previously prepared
     * automatic upgrade across the entire network.
     */
    FREEZE_UPGRADE(com.hedera.hashgraph.sdk.proto.FreezeType.FREEZE_UPGRADE),

    /**
     * Aborts a pending network freeze operation.
     */
    FREEZE_ABORT(com.hedera.hashgraph.sdk.proto.FreezeType.FREEZE_ABORT),

    /**
     * Performs an immediate upgrade on auxilary services and containers providing
     * telemetry/metrics. Does not impact network operations.
     */
    TELEMETRY_UPGRADE(com.hedera.hashgraph.sdk.proto.FreezeType.TELEMETRY_UPGRADE);

    final com.hedera.hashgraph.sdk.proto.FreezeType code;

    FreezeType(com.hedera.hashgraph.sdk.proto.FreezeType code) {
        this.code = code;
    }

    static FreezeType valueOf(com.hedera.hashgraph.sdk.proto.FreezeType code) {
        return switch (code) {
            case UNKNOWN_FREEZE_TYPE -> UNKNOWN_FREEZE_TYPE;
            case FREEZE_ONLY -> FREEZE_ONLY;
            case PREPARE_UPGRADE -> PREPARE_UPGRADE;
            case FREEZE_UPGRADE -> FREEZE_UPGRADE;
            case FREEZE_ABORT -> FREEZE_ABORT;
            case TELEMETRY_UPGRADE -> TELEMETRY_UPGRADE;
            case UNRECOGNIZED ->
                // NOTE: Protobuf deserialization will not give us the code on the wire
                throw new IllegalArgumentException(
                    "network returned unrecognized response code; your SDK may be out of date");
        };
    }

    @Override
    public String toString() {
        return code.name();
    }
}
