(ns cms.domain.author
  (:require [datomic.api :as api]
            [zolodeck.demonic.core :as demonic]
            [cms.datomic.core :as db]
            [cms.domain.article :as ar]))

(defn create [name email]
  (-> {:author/name name :author/email email}
      demonic/insert))

(defn find-all []
  (->> (demonic/run-query '[:find ?e :where [?e :author/email]])
       (map #(demonic/load-entity (first %)))))

(defn find-by-email [email]
  (->> (demonic/run-query '[:find ?e :in $ ?email :where [?e :author/email ?email]] email)
       (map #(demonic/load-entity (first %)))
       first))

(defn update [a name]
  (-> (assoc a :author/name name)
      demonic/insert))

(defn delete [a]
  (demonic/delete a))

(defn add-article [author title body category]
  (->> {:article/title title :article/body body :article/category category}
       (demonic/append-single author :author/articles)))