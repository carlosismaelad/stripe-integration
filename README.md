# StripeService

A classe `StripeService` é responsável por integrar a aplicação com a plataforma de pagamentos Stripe, permitindo a realização de pagamentos únicos, criação de assinaturas recorrentes e gerenciamento de métodos de pagamento.

## Métodos

### 1. `createCardToken(StripeTokenDto model)`
- **Finalidade:** Gera um token de cartão de crédito utilizando os dados fornecidos pelo usuário (número do cartão, data de expiração e CVC).
- **Uso:** Esse token pode ser utilizado para realizar pagamentos sem armazenar diretamente os dados sensíveis do cartão.
- **Detalhes:**
    - Usa a API do Stripe para criar um token (`Token.create(params)`).
    - Retorna um objeto `StripeTokenDto` contendo o token gerado e um indicador de sucesso.

---

### 2. `charge(StripeChargeDto chargeRequest)`
- **Finalidade:** Realiza uma cobrança única com base no token de cartão fornecido.
- **Uso:** Ideal para compras pontuais, onde o usuário faz um pagamento direto.
- **Detalhes:**
    - Converte o valor para centavos (Stripe exige valores inteiros).
    - Define metadados para identificação do pagamento.
    - Cria uma cobrança usando `Charge.create(chargeParams)`.
    - Retorna um `StripeChargeDto` contendo informações sobre a transação.

---

### 3. `createSubscription(StripeSubscriptionDto subscriptionDto)`
- **Finalidade:** Cria uma assinatura recorrente para um cliente.
- **Uso:** Usado para serviços baseados em assinaturas (exemplo: Netflix, Spotify).
- **Fluxo:**
    1. Cria um método de pagamento (`createPaymentMethod`).
    2. Cria um cliente associado ao método de pagamento (`createCustomer`).
    3. Associa o método de pagamento ao cliente (`attachCustomerToPaymentMethod`).
    4. Cria uma assinatura para o cliente (`createSubscription`).
    5. Retorna um `StripeSubscriptionResponse` com IDs do cliente, método de pagamento e assinatura.

---

### 4. `createPaymentMethod(StripeSubscriptionDto subscriptionDto)`
- **Finalidade:** Cria um método de pagamento baseado nos dados do cartão.
- **Uso:** Associado à criação de assinaturas.
- **Detalhes:**
    - Usa `PaymentMethod.create(params)` para registrar o método de pagamento no Stripe.

---

### 5. `createCustomer(PaymentMethod paymentMethod, StripeSubscriptionDto subscriptionDto)`
- **Finalidade:** Cria um cliente no Stripe e associa um método de pagamento a ele.
- **Uso:** Necessário para pagamentos recorrentes (assinaturas).
- **Detalhes:**
    - Usa `Customer.create(customerMap)` para registrar o cliente.
    - Armazena informações como nome, e-mail e ID do método de pagamento.

---

### 6. `attachCustomerToPaymentMethod(Customer customer, PaymentMethod paymentMethod)`
- **Finalidade:** Associa um método de pagamento já criado a um cliente.
- **Uso:** Necessário para que o cliente possa realizar transações futuras.
- **Detalhes:**
    - Obtém o método de pagamento (`PaymentMethod.retrieve(paymentMethod.getId())`).
    - Anexa ao cliente (`paymentMethod.attach(params)`).

---

### 7. `createSubscription(StripeSubscriptionDto subscriptionDto, PaymentMethod paymentMethod, Customer customer)`
- **Finalidade:** Cria uma assinatura para um cliente utilizando o método de pagamento fornecido.
- **Uso:** Usado para planos de assinatura com cobrança automática.
- **Detalhes:**
    - Define os itens da assinatura, incluindo o `priceId` e a quantidade de licenças.
    - Usa `Subscription.create(params)` para criar a assinatura no Stripe.

---

### 8. `cancelSubscription(String subscriptionId)`
- **Finalidade:** Cancela uma assinatura ativa no Stripe.
- **Uso:** Permite que o usuário encerre sua assinatura.
- **Detalhes:**
    - Recupera a assinatura pelo ID (`Subscription.retrieve(subscriptionId)`).
    - Cancela a assinatura (`retrieve.cancel()`).

---

## Conclusão
Esta classe fornece funcionalidades para processar pagamentos e assinaturas utilizando o Stripe. Os métodos são organizados em:
- **Pagamentos únicos:** `createCardToken`, `charge`
- **Assinaturas:** `createSubscription`, `cancelSubscription`
- **Gerenciamento de clientes e métodos de pagamento:** `createCustomer`, `createPaymentMethod`, `attachCustomerToPaymentMethod`

Se precisar de mais detalhes sobre algum método ou melhorias no código, sinta-se à vontade para contribuir! 🚀

