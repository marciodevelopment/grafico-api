CREATE TABLE cache (
    cd_Cache SERIAL PRIMARY KEY,
    json JSON
);
COMMENT ON COLUMN cache.json IS 'JSON data - cache utilizado nos graficos';


CREATE TABLE sistema (
    id_sistema VARCHAR(50) PRIMARY KEY,
    nome_sistema VARCHAR(100) NOT NULL,
    url VARCHAR(2000) NOT NULL,  -- URLs can be long
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,  -- Enough for hashed passwords
    driver_class_name VARCHAR(200) NOT NULL,  -- Java class names
    jdbc_url VARCHAR(2000) NOT NULL  -- JDBC URLs can be long with parameters
);
COMMENT ON TABLE sistema IS 'Armazena as informações dos bancos de dados externos';
COMMENT ON COLUMN sistema.id_sistema IS 'nome do sistema';
COMMENT ON COLUMN sistema.nome_sistema IS 'descrição amigável do sistema';
COMMENT ON COLUMN sistema.url IS 'System URL';
COMMENT ON COLUMN sistema.username IS 'Database username';
COMMENT ON COLUMN sistema.password IS 'Database password';
COMMENT ON COLUMN sistema.driver_class_name IS 'JDBC driver class name';
COMMENT ON COLUMN sistema.jdbc_url IS 'JDBC connection URL';


CREATE TABLE grafico (
    cd_grafico SERIAL PRIMARY KEY,
    grafico VARCHAR(255),
    tempo_atualizacao_segundos INTEGER,
    horas_atualizacao VARCHAR(100),
    sql TEXT,
    id_sistema VARCHAR(255),
    data_cache TIMESTAMP,
    id_atualizando SMALLINT DEFAULT 0,  -- Boolean as numeric (0/1)
    data_proxima_execucao TIMESTAMP DEFAULT NOW(),
    cd_cache INTEGER,
    data_inicio_atualizacao TIMESTAMP,
    data_fim_atualizacao TIMESTAMP,
    id_ativo SMALLINT DEFAULT 1 NOT NULL,  -- For @SQLRestriction("idAtivo = 1")

    -- Composite unique constraint: grafico is unique per sistema
    CONSTRAINT uk_grafico_sistema UNIQUE (grafico, id_sistema)
);

COMMENT ON COLUMN grafico.tempo_atualizacao_segundos IS 'tempo sem segundo para atualização do cache';
COMMENT ON COLUMN grafico.horas_atualizacao IS 'Horas para atualiação. Utilizar horas 24hrs. Apenas horas cheias. Separadas por ponto e virgula. 5;12;18';

ALTER TABLE grafico
ADD CONSTRAINT fk_grafico_sistema
FOREIGN KEY (id_sistema) REFERENCES sistema(id_sistema);

ALTER TABLE grafico
ADD CONSTRAINT fk_grafico_cache
FOREIGN KEY (cd_cache) REFERENCES cache(cd_cache);


CREATE TABLE erro_execucao (
    cd_erro_execucao SERIAL PRIMARY KEY,
    stack_trace TEXT,
    cd_grafico INTEGER,
    data_erro TIMESTAMP DEFAULT NOW()
);

ALTER TABLE erro_execucao
ADD CONSTRAINT fk_erro_execucao_grafico
FOREIGN KEY (cd_grafico) REFERENCES grafico(cd_grafico);

-- Optional: Add indexes for better performance
CREATE INDEX idx_erro_execucao_grafico ON erro_execucao(cd_grafico);


