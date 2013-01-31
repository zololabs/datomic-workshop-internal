(ns cms.datomic.schema
  (:require [datomic.api :as db]))

(def author-schema
  [;; name
   {:db/id #db/id[:db.part/db]
    :db/ident :author/name
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc "A author's name"
    :db.install/_attribute :db.part/db}

   ;; email
   {:db/id #db/id[:db.part/db]
    :db/ident :author/email
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc "A author's email"
    :db.install/_attribute :db.part/db}])


(defn all []
  author-schema)