create table if not exists contas_db (
	id SERIAL primary key,
	data_vencimento DATE not null,
	data_pagamento DATE,
	valor DECIMAL(10, 2) not null,
	descricao text not null,
	situacao boolean not null
);

