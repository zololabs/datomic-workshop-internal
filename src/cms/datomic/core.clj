(ns cms.datomic.core
  (:require [datomic.api :as api]
            [cms.datomic.schema :as schema]))

(def uri "datomic:mem://cms")

(defn init []
  (api/create-database uri)
  (def CONN (api/connect uri))

  @(api/transact CONN (schema/all)))