Projeto loja online para a disciplina de POO.

Regras a serem seguidas:

✔️ Regras gerais de programação e sistemas

1. Classes e métodos

Você não pode modificar as entradas (parâmetros) de um método já existente.

Você não pode alterar o retorno de um método já existente.

Caso realmente precise mudar algo, deve alterar em todas as classes onde esse método é chamado — o que é arriscado e pode quebrar outras partes do sistema.

Métodos existentes geralmente têm função dupla ou múltipla dentro do sistema, por isso não devem ser alterados sem certeza total de que não afetarão outras funcionalidades.



2. Tabelas no sistema

O sistema possui tabelas principais (core).

Você pode:

Criar novas tabelas e montar um fluxo baseado nelas.

OU utilizar as tabelas existentes.




3. Uso de tabelas

Se for usar uma tabela existente, crie seu próprio método para manipulá-la.

Não modifique métodos já existentes que usam essas tabelas, pois:

Eles podem ser usados por outros módulos.

A alteração pode gerar comportamentos inesperados em partes do sistema.