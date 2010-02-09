package com.twitter.gizzard.sharding

import scala.collection.mutable


class ShardRepository[S <: Shard] {
  private val shardFactories = mutable.Map.empty[String, ShardFactory[S]]

  def +=(item: (String, ShardFactory[S])) {
    shardFactories += item
  }

  def find(shardInfo: ShardInfo, weight: Int, children: Seq[S]) = {
    shardFactories(shardInfo.className).instantiate(shardInfo, weight, children)
  }

  def create(shardInfo: ShardInfo) {
    shardFactories(shardInfo.className).materialize(shardInfo)
  }
}
