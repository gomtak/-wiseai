package com.concertmania.api.common.redis

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.script.DefaultRedisScript
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RedisSpinLock(
    private val stringRedisTemplate: StringRedisTemplate
) {

    fun lock(key: String, lockValue: String, expireMillis: Long): Boolean {
        val endTime = System.currentTimeMillis() + 3000L // 최대 3초

        while (System.currentTimeMillis() < endTime) {
            val success = stringRedisTemplate.opsForValue().setIfAbsent(
                buildKey(key),
                lockValue,
                Duration.ofMillis(expireMillis)
            )

            if (success == true) {
                return true
            }

            Thread.onSpinWait()
        }

        return false
    }

    fun unlock(key: String, lockValue: String) {
        val script = """
            if redis.call("get", KEYS[1]) == ARGV[1] then
                return redis.call("del", KEYS[1])
            else
                return 0
            end
        """.trimIndent()

        val redisScript = DefaultRedisScript<Long>(script, Long::class.java)
        stringRedisTemplate.execute(redisScript, listOf(buildKey(key)), lockValue)
    }

    private fun buildKey(seatId: String): String = "lock:seat:$seatId"

}
