DROP TABLE FailedTransaction IF EXISTS;

CREATE TABLE FailedTransaction  (
    reference BIGINT,
    reason LONGVARCHAR
);
