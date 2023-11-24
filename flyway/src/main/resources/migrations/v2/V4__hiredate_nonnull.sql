CREATE TABLE emp_new
(
    empno    INTEGER,
    ename    VARCHAR(10),
    job      VARCHAR(9),
    mgr      INTEGER,
    hiredate TEXT NOT NULL,
    sal      REAL,
    deptno   INTEGER,
    PRIMARY KEY (empno),
    FOREIGN KEY (deptno) REFERENCES dept (deptno)
);

INSERT INTO emp_new (empno, ename, job, mgr, hiredate, sal, deptno)
SELECT empno, ename, job, mgr, hiredate, sal, deptno FROM emp;

DROP TABLE emp;

ALTER TABLE emp_new RENAME TO emp;
