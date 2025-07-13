package com.concertmania.api.concert.application.service

import com.concertmania.api.member.domain.RoleType
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class QueueService(
    private val redisTemplate: StringRedisTemplate
) {

    companion object {
        private const val QUEUE_KEY_PREFIX = "queue"
        private const val TTL_SECONDS = 60 * 10L // 10분
    }

    @UserOnly(roleType = "roleType")
    fun joinQueue(
        concertId: Long,
        userName: String,
        roleType: RoleType
    ): QueueStatus {

        val key = "$QUEUE_KEY_PREFIX:$concertId"
        val now = System.currentTimeMillis().toDouble()

        val ops = redisTemplate.opsForZSet()

        // 이미 대기열에 있는지 확인
        val existingScore = ops.score(key, userName)
        if (existingScore != null) {
            val rank = ops.rank(key, userName)?.toInt()?.plus(1) ?: -1
            return QueueStatus.IN_QUEUE(position = rank)
        }

        // 새로 추가
        ops.add(key, userName, now)

        // TTL 설정 (한 번만)
        redisTemplate.expire(key, Duration.ofSeconds(TTL_SECONDS))

        val rank = ops.rank(key, userName)?.toInt()?.plus(1) ?: -1
        return QueueStatus.IN_QUEUE(position = rank)
    }

    fun getQueueStatus(concertId: Long, userName: String): QueueStatus {
        val key = "$QUEUE_KEY_PREFIX:$concertId"
        val rank = redisTemplate.opsForZSet().rank(key, userName) ?: return QueueStatus.NOT_IN_QUEUE
        return QueueStatus.IN_QUEUE(position = rank.toInt() + 1)
    }

    fun leaveQueue(concertId: Long, userName: String) {
        val key = "$QUEUE_KEY_PREFIX:$concertId"
        redisTemplate.opsForZSet().remove(key, userName)
    }


}