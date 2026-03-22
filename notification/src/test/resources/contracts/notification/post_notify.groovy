package contracts.notification

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description 'Отправка уведомления'
    name 'post_notify'

    request {
        method POST()
        url '/notify'
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
                message: 'some_message',
                error: true
        )
    }

    response {
        status OK()
    }
}