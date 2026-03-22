package contracts.accounts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description 'Обновление аккаунта ACC-001'
    name 'update_account_by_login'

    request {
        method POST()
        url '/account/testLogin/transfer'
        headers {
            header ('Authorization', value(
                    // Для консьюмера (WireMock): любой Bearer-токен
                    consumer(regex('Bearer\\s+.+')),
                    // Для провайдера (MockMvc-тест): ровно этот токен
                    producer('Bearer test-token')
            ))
            header ("Content-Type", "application/json")
        }
        body(
                sum: 1,
                login: 'ACC-001'

        )
    }

    response {
        status OK()
        headers {
            header("Content-Type", "application/json")
        }
        body(1)
    }
}