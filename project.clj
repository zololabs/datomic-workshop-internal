(defproject datomic-workshop "0.1.0-SNAPSHOT"
  :description "Sample Project for Datomic Workshop"
  :url "http://www.proclojure.com/datomic.html"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]

                 ;;Datomic Related Deps
                 [com.datomic/datomic-free "0.8.3767"]
                 [zolodeck/demonic "0.1.0-SNAPSHOT" :exclusions [com.datomic/datomic-free]]]

  :min-lein-version "2.0.0"

  :project-init (do (use 'clojure.pprint)
                    (use 'clojure.test))

  :bootclasspath false)
