package com.hedera.hashgraph.sdk;

import com.hedera.hashgraph.sdk.proto.CryptoCreateTransactionBody;
import com.hedera.hashgraph.sdk.proto.TransactionBody;
import org.threeten.bp.Duration;

/**
 * Create a new Hedera™ account.
 */
public final class AccountCreateTransaction extends TransactionBuilder<AccountCreateTransaction> {
    private static final long DEFAULT_RECORD_THRESHOLD = Long.MAX_VALUE;

    private final CryptoCreateTransactionBody.Builder builder;

    public AccountCreateTransaction() {
        builder = CryptoCreateTransactionBody.newBuilder();

        setAutoRenewPeriod(DEFAULT_AUTO_RENEW_PERIOD);
        setReceiveRecordThreshold(DEFAULT_RECORD_THRESHOLD);
    }

    /**
     * Set the key for this account.
     * <p>
     * The key that must sign each transfer out of the account.
     * If receiverSignatureRequired is true, then it must also sign any
     * transfer into the account.
     *
     * @param key the key for this account.
     * @return {@code this}.
     */
    public AccountCreateTransaction setKey(Key key) {
        builder.setKey(key.toProtobuf());
        return this;
    }

    /**
     * Set the initial amount to transfer into this account.
     *
     * @param initialBalance the initial balance in tinybars.
     * @return {@code this}.
     */
    public AccountCreateTransaction setInitialBalance(long initialBalance) {
        builder.setInitialBalance(initialBalance);
        return this;
    }

    /**
     * Set the threshold amount for which a transaction record is created for any transfer
     * of hbars from this account.
     * <p>
     * This is defaulted to {@code Long.MAX_VALUE} by the SDK as the fee for threshold records can be surprising.
     * Do not adjust the threshold unless you understand that you be charged an additional (small) fee any time
     * your account sends money.
     *
     * @param sendRecordThreshold the threshold amount in tinybars.
     * @return {@code this}.
     */
    public AccountCreateTransaction setSendRecordThreshold(long sendRecordThreshold) {
        builder.setSendRecordThreshold(sendRecordThreshold);
        return this;
    }

    /**
     * Set the threshold amount for which a transaction record is created for any transfer
     * of hbars <b>to this account</b>.
     * <p>
     * This is defaulted to {@code Long.MAX_VALUE} by the SDK as the fee for threshold records can be surprising.
     * Do not adjust the threshold unless you understand that you be charged an additional (small) fee any time
     * your account <b>receives</b> money.
     *
     * @param receiveRecordThreshold the threshold amount in tinybars.
     * @return {@code this}.
     */
    public AccountCreateTransaction setReceiveRecordThreshold(long receiveRecordThreshold) {
        builder.setReceiveRecordThreshold(receiveRecordThreshold);
        return this;
    }

    /**
     * Set to true to require this account to sign any transfer of hbars to this account.
     * <p>
     * All transfers of hbars from this account must always be signed. This property only affects transfers to this
     * account.
     *
     * @param receiveSignatureRequired true to require a signature when receiving hbars.
     * @return {@code this}.
     */
    public AccountCreateTransaction setReceiveSignatureRequired(boolean receiveSignatureRequired) {
        builder.setReceiverSigRequired(receiveSignatureRequired);
        return this;
    }

    /**
     * Set the ID of the account to which this account is proxy staked.
     *
     * @param proxyAccountId the proxy account ID.
     * @return {@code this}.
     */
    public AccountCreateTransaction setProxyAccountId(AccountId proxyAccountId) {
        builder.setProxyAccountID(proxyAccountId.toProtobuf());
        return this;
    }

    /**
     * Set the auto renew period for this account.
     * <p>
     * A Hedera™ account is charged to extend its expiration date every renew period.
     * If it doesn't have enough balance, it extends as long as possible.
     * If the balance is zero when it expires, then the account is deleted.
     * <p>
     * This is defaulted to 3 months by the SDK.
     *
     * @param autoRenewPeriod the auto renew period for this account.
     * @return {@code this}.
     */
    public AccountCreateTransaction setAutoRenewPeriod(Duration autoRenewPeriod) {
        builder.setAutoRenewPeriod(DurationConverter.toProtobuf(autoRenewPeriod));
        return this;
    }

    @Override
    protected void onBuild(TransactionBody.Builder bodyBuilder) {
        bodyBuilder.setCryptoCreateAccount(builder);
    }
}
