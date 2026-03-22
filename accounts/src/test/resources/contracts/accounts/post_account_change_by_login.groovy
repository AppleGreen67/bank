package contracts.accounts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description 'Изменение счета аккаунта '
    name 'post_account_change_by_login'

    request {
        method POST()
        url '/account/testLogin/change'
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
                sum: 5,
                action: 'PUT'
        )
    }

    response {
        status OK()
        headers {
            header("Content-Type", "application/json")
        }
        body(15)
    }
}