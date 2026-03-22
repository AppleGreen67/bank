package contracts.accounts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description 'Получение аккаунта ACC-001'
    name 'get_account_by_login'

    request {
        method GET()
        url '/account'
        headers {
            header ('Authorization', value(
                    // Для консьюмера (WireMock): любой Bearer-токен
                    consumer(regex('Bearer\\s+.+')),
                    // Для провайдера (MockMvc-тест): ровно этот токен
                    producer('Bearer test-token')
            ))
            header ("Content-Type", "application/json")
        }
    }

    response {
        status OK()
        headers {
            contentType(applicationJson())
        }
        body(
                login: 'testLogin',
                name: 'testName',
                birthdate: '2222-22-22',
                sum: 1
        )
    }
}