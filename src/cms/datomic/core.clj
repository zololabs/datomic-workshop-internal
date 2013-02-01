(ns cms.datomic.core
  (:require [datomic.api :as api]
            [cms.datomic.schema :as schema]))

(declare CONN)

(def uri "datomic:mem://cms")

(defn schema-attrib-name [attrib-id]
  (-> '[:find ?a :in $ ?e :where [?e :db/ident ?a]]
      (api/q (api/db CONN) attrib-id)
      ffirst))

(defn init []
  (api/create-database uri)
  (def CONN (api/connect uri))

  @(api/transact CONN (schema/all)))