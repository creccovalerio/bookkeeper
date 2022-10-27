"use strict";(self.webpackChunksite_3=self.webpackChunksite_3||[]).push([[4670],{3905:function(e,t,i){i.d(t,{Zo:function(){return c},kt:function(){return p}});var a=i(7294);function o(e,t,i){return t in e?Object.defineProperty(e,t,{value:i,enumerable:!0,configurable:!0,writable:!0}):e[t]=i,e}function n(e,t){var i=Object.keys(e);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);t&&(a=a.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),i.push.apply(i,a)}return i}function r(e){for(var t=1;t<arguments.length;t++){var i=null!=arguments[t]?arguments[t]:{};t%2?n(Object(i),!0).forEach((function(t){o(e,t,i[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(i)):n(Object(i)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(i,t))}))}return e}function l(e,t){if(null==e)return{};var i,a,o=function(e,t){if(null==e)return{};var i,a,o={},n=Object.keys(e);for(a=0;a<n.length;a++)i=n[a],t.indexOf(i)>=0||(o[i]=e[i]);return o}(e,t);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);for(a=0;a<n.length;a++)i=n[a],t.indexOf(i)>=0||Object.prototype.propertyIsEnumerable.call(e,i)&&(o[i]=e[i])}return o}var s=a.createContext({}),d=function(e){var t=a.useContext(s),i=t;return e&&(i="function"==typeof e?e(t):r(r({},t),e)),i},c=function(e){var t=d(e.components);return a.createElement(s.Provider,{value:t},e.children)},u={inlineCode:"code",wrapper:function(e){var t=e.children;return a.createElement(a.Fragment,{},t)}},h=a.forwardRef((function(e,t){var i=e.components,o=e.mdxType,n=e.originalType,s=e.parentName,c=l(e,["components","mdxType","originalType","parentName"]),h=d(i),p=o,m=h["".concat(s,".").concat(p)]||h[p]||u[p]||n;return i?a.createElement(m,r(r({ref:t},c),{},{components:i})):a.createElement(m,r({ref:t},c))}));function p(e,t){var i=arguments,o=t&&t.mdxType;if("string"==typeof e||o){var n=i.length,r=new Array(n);r[0]=h;var l={};for(var s in t)hasOwnProperty.call(t,s)&&(l[s]=t[s]);l.originalType=e,l.mdxType="string"==typeof e?e:o,r[1]=l;for(var d=2;d<n;d++)r[d]=i[d];return a.createElement.apply(null,r)}return a.createElement.apply(null,i)}h.displayName="MDXCreateElement"},799:function(e,t,i){i.r(t),i.d(t,{contentTitle:function(){return s},default:function(){return h},frontMatter:function(){return l},metadata:function(){return d},toc:function(){return c}});var a=i(7462),o=i(3366),n=(i(7294),i(3905)),r=["components"],l={},s="BP-31: BookKeeper Durability (Anchor)",d={type:"mdx",permalink:"/bps/BP-31-durability",source:"@site/src/pages/bps/BP-31-durability.md",title:"BP-31: BookKeeper Durability (Anchor)",description:"Motivation",frontMatter:{}},c=[{value:"Motivation",id:"motivation",level:2},{value:"Durability Contract",id:"durability-contract",level:2},{value:"Work Grouping (In the order of priority)",id:"work-grouping-in-the-order-of-priority",level:2},{value:"Detect Durability Validation",id:"detect-durability-validation",level:3},{value:"Delete Discipline",id:"delete-discipline",level:3},{value:"Metadata Recovery",id:"metadata-recovery",level:3},{value:"Plug Durability Violations",id:"plug-durability-violations",level:3},{value:"Durability Test",id:"durability-test",level:3},{value:"Introduce bookie incarnation",id:"introduce-bookie-incarnation",level:3},{value:"End 2 End Checksum",id:"end-2-end-checksum",level:3},{value:"Soft Deletes",id:"soft-deletes",level:3},{value:"BitRot detection",id:"bitrot-detection",level:3},{value:"Durability Contract Violations",id:"durability-contract-violations",level:2},{value:"Write errors beyond AQ are ignored.",id:"write-errors-beyond-aq-are-ignored",level:3},{value:"Strict enforcement of placement policy",id:"strict-enforcement-of-placement-policy",level:3},{value:"Detect and act on Ledger disk problems",id:"detect-and-act-on-ledger-disk-problems",level:3},{value:"Checksum at bookies in the write path",id:"checksum-at-bookies-in-the-write-path",level:3},{value:"No repair in the read path",id:"no-repair-in-the-read-path",level:3},{value:"Operations",id:"operations",level:2},{value:"No bookie incarnation mechanism",id:"no-bookie-incarnation-mechanism",level:3},{value:"Enhancements",id:"enhancements",level:2},{value:"Delete Choke Points",id:"delete-choke-points",level:3},{value:"Archival bit in the metadata to assist Two phase Deletes",id:"archival-bit-in-the-metadata-to-assist-two-phase-deletes",level:3},{value:"Stateful explicit deltes",id:"stateful-explicit-deltes",level:3},{value:"Obvious points to consider:",id:"obvious-points-to-consider",level:4},{value:"Metadata recovery tool",id:"metadata-recovery-tool",level:3},{value:"Bit Rot Detection (BP-24)",id:"bit-rot-detection-bp-24",level:3},{value:"End to end checksum",id:"end-to-end-checksum",level:3},{value:"Test strategy to validate durability",id:"test-strategy-to-validate-durability",level:2},{value:"White box error injection",id:"white-box-error-injection",level:3},{value:"Black box error injection (Chaos Monkey)",id:"black-box-error-injection-chaos-monkey",level:3}],u={toc:c};function h(e){var t=e.components,i=(0,o.Z)(e,r);return(0,n.kt)("wrapper",(0,a.Z)({},u,i,{components:t,mdxType:"MDXLayout"}),(0,n.kt)("h1",{id:"bp-31-bookkeeper-durability-anchor"},"BP-31: BookKeeper Durability (Anchor)"),(0,n.kt)("h2",{id:"motivation"},"Motivation"),(0,n.kt)("p",null,"Apache BookKeeper is transitioning into a full fledged distributed storage that can keep the data for long term. Durability is paramount to achieve the status of trusted store. This Anchor BP discusses many gaps and areas of improvement.  Each issue listed here will have another issue and this BP is expected to be updated when that issue is created."),(0,n.kt)("h2",{id:"durability-contract"},"Durability Contract"),(0,n.kt)("ol",null,(0,n.kt)("li",{parentName:"ol"},(0,n.kt)("strong",{parentName:"li"},"Maintain WQ copies all the time"),". If any ledger falls into under replicated state, there needs to be an SLA on how quickly the replication levels can be brought back to normal levels."),(0,n.kt)("li",{parentName:"ol"},(0,n.kt)("strong",{parentName:"li"},"Enforce Placement Policy")," strictly during write and replication."),(0,n.kt)("li",{parentName:"ol"},(0,n.kt)("strong",{parentName:"li"},"Protect the data")," against corruption on the wire or at rest.")),(0,n.kt)("h2",{id:"work-grouping-in-the-order-of-priority"},"Work Grouping (In the order of priority)"),(0,n.kt)("h3",{id:"detect-durability-validation"},"Detect Durability Validation"),(0,n.kt)("p",null,"First step is to understand the areas of durability breaches. Design metrics that record durability contract violations. "),(0,n.kt)("ul",null,(0,n.kt)("li",{parentName:"ul"},"At the Creation: Validate durability contract when the ledger is being created"),(0,n.kt)("li",{parentName:"ul"},"At the Deletion: Validate durability contract when ledger is deleted"),(0,n.kt)("li",{parentName:"ul"},"During lifetime: Validate durability contract during the lifetime of the ledger.(periodic validator)"),(0,n.kt)("li",{parentName:"ul"},"During Read: IO or Checksum errors in the read path")),(0,n.kt)("h3",{id:"delete-discipline"},"Delete Discipline"),(0,n.kt)("ul",null,(0,n.kt)("li",{parentName:"ul"},"Build a single delete choke point with stringent validations"),(0,n.kt)("li",{parentName:"ul"},"Archival bit in the metadata to assist Two phase Deletes"),(0,n.kt)("li",{parentName:"ul"},"Stateful/Explicit Deletes")),(0,n.kt)("h3",{id:"metadata-recovery"},"Metadata Recovery"),(0,n.kt)("ul",null,(0,n.kt)("li",{parentName:"ul"},"Metadata recovery tool to reconstruct the metadata if the metadata server gets wiped out. This tool need to make sure that the data is readable even if we can't get all the metadata (ex: ctime) back.")),(0,n.kt)("h3",{id:"plug-durability-violations"},"Plug Durability Violations"),(0,n.kt)("p",null,"Our first step is to identify durability viloations. That gives us the magnitude of the problem and areas that we need to focus. In this phase, fix high impact areas."),(0,n.kt)("ul",null,(0,n.kt)("li",{parentName:"ul"},"Identify source of problems detected by the work we did in step-1 above (Detect Durability Validation)"),(0,n.kt)("li",{parentName:"ul"},"Rereplicate under replicated ledgers detected during write"),(0,n.kt)("li",{parentName:"ul"},"Rereplicate under replicated / corrupted ledgers detected during read"),(0,n.kt)("li",{parentName:"ul"},"Replicated under replicated ledgers identified by periodic validator.")),(0,n.kt)("h3",{id:"durability-test"},"Durability Test"),(0,n.kt)("ul",null,(0,n.kt)("li",{parentName:"ul"},"Test plan, new tests and integrating it into CI pipeline. ")),(0,n.kt)("h3",{id:"introduce-bookie-incarnation"},"Introduce bookie incarnation"),(0,n.kt)("ul",null,(0,n.kt)("li",{parentName:"ul"},"Design/Implement bookie incarnation mechanism ")),(0,n.kt)("h3",{id:"end-2-end-checksum"},"End 2 End Checksum"),(0,n.kt)("ul",null,(0,n.kt)("li",{parentName:"ul"},"Efficient checksum implementation (crc32c?)"),(0,n.kt)("li",{parentName:"ul"},"Implement checksum validation on bookies in the write path. ")),(0,n.kt)("h3",{id:"soft-deletes"},"Soft Deletes"),(0,n.kt)("ul",null,(0,n.kt)("li",{parentName:"ul"},"Design and implement soft delete feature")),(0,n.kt)("h3",{id:"bitrot-detection"},"BitRot detection"),(0,n.kt)("ul",null,(0,n.kt)("li",{parentName:"ul"},"Design and implement bitrot detection/correction.")),(0,n.kt)("h2",{id:"durability-contract-violations"},"Durability Contract Violations"),(0,n.kt)("h3",{id:"write-errors-beyond-aq-are-ignored"},"Write errors beyond AQ are ignored."),(0,n.kt)("p",null,"BK client library transparently corrects any write errors while writing to bookie by changing the ensemble. Take a case where ",(0,n.kt)("inlineCode",{parentName:"p"},"WQ:3 and AQ:2"),". This works fine only if the write fails to the bookie before it gets 2 successful responses. But if the 3rd bookie write fails ",(0,n.kt)("strong",{parentName:"p"},"after")," 2 successful responses and the response sent to client, this error is logged and no immediate action is taken to bring up the replication of the entry.\nThis case ",(0,n.kt)("strong",{parentName:"p"},"may not be"),"  detected by the auditor\u2019s periodic ledger check. Given that we allow out of order write, that in the combination of 2 out of 3 to satisfy client, it is possible to have under replication in the middle of the ensemble entry. Hence ledgercheck is not going to find all under replication cases, on top of that,   periodic ledger check  is a complete sweep of the store, an very expensive and slow crawl hence defaulted to once a week run."),(0,n.kt)("h3",{id:"strict-enforcement-of-placement-policy"},"Strict enforcement of placement policy"),(0,n.kt)("p",null,"The best effort placement policy increases the write availability but at the cost of durability. Due to this non-strict placement, BK can\u2019t guarantee data availability when a fault domain (rack) is lost. This also makes rolling upgrade across fault domains more difficult/non possible. Need to enforce strict ensemble placement and fail the write if all WQ copies are not able to be placed across different fault domains.  Minor fix/enhancement if we agree to give placement higher priority than a successful write(availability)"),(0,n.kt)("p",null,"The auditor re-replication uses client library to find a replacement bookie for each ledger in the lost bookie. But bookies are unaware of the ledger ensemble placement policy as this information is not part of metadata. "),(0,n.kt)("h3",{id:"detect-and-act-on-ledger-disk-problems"},"Detect and act on Ledger disk problems"),(0,n.kt)("p",null,"While Auditor mechanism detects complete bookie crash, there is no mechanism to detect individual ledger disk errors. So if a ledger disk goes bad, bookie continues to run, and auditor can\u2019t recognize under replication condition, until it runs the complete sweep, periodic ledger check. On the other hand bookie refuses to come up if it finds a bad disk, which is right thing to do. This is easy to fix, in the interleaved ledger manger bad disk handle."),(0,n.kt)("h3",{id:"checksum-at-bookies-in-the-write-path"},"Checksum at bookies in the write path"),(0,n.kt)("p",null,"Lack of checksum calculations on the write path makes the store not to detect any corruption at the source issues. Imagine NIC issues on the client. If data gets corrupted at the client NIC\u2019s level it safely gets stored on bookies (for the lack of crc calculations in the write path). This is a silent corruption of all 3 copies.  For additional durability guarantees we can add checksum verification on bookies in the write path. Checksum calculations are cpu intensive and will add to the latency. But Java9 is adding native support for CRC32-C - A hardware assisted CRC calculation. We can consider adding this additional during JAVA-9 port after evaluating performance tradeoffs. "),(0,n.kt)("h3",{id:"no-repair-in-the-read-path"},"No repair in the read path"),(0,n.kt)("p",null,"When a checksum error is detected, in addition to finding good replica, sfstore need to repair(replace with good one) bad replica too."),(0,n.kt)("h2",{id:"operations"},"Operations"),(0,n.kt)("h3",{id:"no-bookie-incarnation-mechanism"},"No bookie incarnation mechanism"),(0,n.kt)("p",null,"A bookie ",(0,n.kt)("inlineCode",{parentName:"p"},"B1 at time t1")," ; and same bookie ",(0,n.kt)("inlineCode",{parentName:"p"},"B1 at time t2")," after bookie format are treated in the same way.\nFor this to cause any durability issues:"),(0,n.kt)("ul",null,(0,n.kt)("li",{parentName:"ul"},"Replication/Auditor mechanism is stopped or not running for some reason. (A stuck auditor will start a new one due to ZK)"),(0,n.kt)("li",{parentName:"ul"},"One of bookies(B1) went down (crash or something)"),(0,n.kt)("li",{parentName:"ul"},"B1\u2019s Journal dir and all ledger dir got wiped."),(0,n.kt)("li",{parentName:"ul"},"B1 came back to life as a fresh bookie"),(0,n.kt)("li",{parentName:"ul"},"Auditor is enabled  monitoring again")),(0,n.kt)("p",null,"At this point auditor doesn\u2019t have capability to know that the B1 in the cluster is not the same B1 that it used to be. Hence doesn\u2019t consider it for under replication. This is a pathological scenario but we at least need to have a mechanism to identify and alert this scenario if not taking care of bookie incarnation issue."),(0,n.kt)("h2",{id:"enhancements"},"Enhancements"),(0,n.kt)("h3",{id:"delete-choke-points"},"Delete Choke Points"),(0,n.kt)("p",null,"Every delete must go through single routine/path in the code and that needs to implement additional checks to perform physical delete."),(0,n.kt)("h3",{id:"archival-bit-in-the-metadata-to-assist-two-phase-deletes"},"Archival bit in the metadata to assist Two phase Deletes"),(0,n.kt)("p",null,"Main aim of this feature is to be as conservative as possible on the delete path. As explained in the stateful explicit deletes section, lack of ledgerId in the metadata means that ledger is deleted. A bug in the client code may erroneously delete the ledger. To protect from that, we want to introduce a archive/backedup bit. A separate backup/archival application can mark the bit after successfully backing up the ledger, and later on main client application will send the delete. If this feature is enabled, BK client will reject and throw an exception if it receives a delete request without archival/backed-up bit is not set. This protects the data from bugs and erroneous deletes."),(0,n.kt)("h3",{id:"stateful-explicit-deltes"},"Stateful explicit deltes"),(0,n.kt)("p",null,"Current bookkeeper deletes synchronously deletes the metadata in the zookeeper. Bookies implicitly assume that a particular ledger is deleted if it is not present in the metadata. This process has no crosscheck if the ledger is actually deleted. Any ZK corruption or loss of the ledger path znodes will make bookies to delete data on the disk. No cross check. Even bugs in bookie code which \u2018determines\u2019 if a ledger is present on the zk or not, may lead to data deletion. "),(0,n.kt)("p",null,"Right way to deal with this is to asynchronously delete metadata after each bookie explicitly checks that a particular ledger is deleted. This way each bookie explicitly checks the \u2018delete state\u2019 of the ledger before deleting on the disk data. One of the proposal is to move the deleted ledgers under /deleted/","<","ledgerId",">"," other idea is to add a delete state, Open->Closed->Deleted."),(0,n.kt)("p",null,"As soon as we make the metadata deletions asynchronous, the immediate question is who will delete it?\nOption-1: A centralized process like auditor will be responsible for deleting metadata after each bookie deletes on disk data.\nOption-2: A decentralized, more complicated approach: Last bookie that deletes its on disk data, deletes the metadata too.\nI am sure there can be more ideas. Any path will need a detailed design and need to consider many corner cases."),(0,n.kt)("h4",{id:"obvious-points-to-consider"},"Obvious points to consider:"),(0,n.kt)("p",null,"ZK as-is heavily loaded with BK metadata. Keeping these znodes around for more time ineeded puts more pressure on ZK.\nIf a bookie is down for long time, what would be the delete policy for the metadata?\nThere will be lots of corner case scenarios we need to deal with. For example:\nA bookie-1 hosting data for ledger-1  is down for long time\nLedger-1 data has been replicated to other bookies\nLedger-1 is deleted, and its data and metadata is clared.\nNow bookie-1 came back to life. Since our policy is \u2018explicit state check delete\u2019 bookie-1 can\u2019t delete ledger-1 data as it can\u2019t explicitly validate that the ledger-1 has been deleted.\nOne possible solution: keep tomestones of deleted ledgers around for some duration. If a bookie is down for more than that duration, it needs to be decommissioned  and add as a new bookie.\nEnhance: Archival bit in the metadata to assist Two phase Deletes\nMain aim of this feature is to be as conservative as possible on the delete path. As explained in the stateful explicit deletes section, lack of ledgerId in the metadata means that ledger is deleted. A bug in the client code may erroneously delete the ledger. To protect from that, we want to introduce a archive/backedup bit. A separate backup/archival application can mark the bit after successfully backing up the ledger, and later on main client application will send the delete. If this feature is enabled, BK client will reject and throw an exception if it receives a delete request without archival/backed-up bit is not set. This protects the data from bugs and erroneous deletes."),(0,n.kt)("h3",{id:"metadata-recovery-tool"},"Metadata recovery tool"),(0,n.kt)("p",null,"In case zookkeper completely wiped we need a way to reconstruct enough metadata to read ledgers back. Currently metadata contains ensemble information which is critical for reading ledgers back, and also it has additional metadata like ctime and custom metadata. Every bookie has one index file per ledger and that has enough information to reconstruct the ensemble information so that the ledgers can be made readable. This tool can be built in two ways.\nIf ZK is completely wiped, reconstruct entire data from bookie index files.\nIf ZK is completely wiped, but snapshots are available, restore ZK from snapshots and built the delta from bookie index files."),(0,n.kt)("h3",{id:"bit-rot-detection-bp-24"},"Bit Rot Detection (BP-24)"),(0,n.kt)("p",null,"If the data stays on the disk for long time(years), it is possible to experience silent data degradation on the disk. In the current scenario we will not identify this until the data is read by the application."),(0,n.kt)("h3",{id:"end-to-end-checksum"},"End to end checksum"),(0,n.kt)("p",null,"Bookies never validate the payload checksum. If the client\u2019s socket has issues, it might corrupt the data (at the source) and it won\u2019t be detected until client reads it back. That will be too late as the original write was successful for the application. Use efficient checksum mechanisms and enforce checksum validations on the bookie\u2019s write path. If checksum validation fails, write itself will fail and application will be notified. "),(0,n.kt)("h2",{id:"test-strategy-to-validate-durability"},"Test strategy to validate durability"),(0,n.kt)("p",null,"BK need to develop a comprehensive testing strategy to test and validate the store\u2019s durability. Various methods and levels are tests are needed to gain confidence for deploying the store in production. Specific points are mentioned here and these are in addition to regular functional testing/validation."),(0,n.kt)("h3",{id:"white-box-error-injection"},"White box error injection"),(0,n.kt)("p",null,"Introduce all possible errors in the write path, kick replication mechanism and make sure cluster reached desired replica levels.\nCorrupt first readable copy and make sure that the corruption is detected on the read path, and ultimately read must succeed after trying second replica.\nCorrupt packet after checksum calculation on the client and make sure that it is detected in the read path, and ultimately read fails as this is corruption at the source.\nAfter a write make sure that the replica is distributed across fault zones.\nKill a bookie, make sure that the auditor detected and replicated all ledgers in that bookie according to allocation policy (across fault zones)"),(0,n.kt)("h3",{id:"black-box-error-injection-chaos-monkey"},"Black box error injection (Chaos Monkey)"),(0,n.kt)("p",null,"While keeping longevity testing which is doing continues IO to the store introduce following errors.\nKill random bookie and reads should continue.\nKill random bookies keeping minimum fault zones to satisfy AQ Quorum during write workload.\nSimulate disk errors in random bookies and allow the bookie to go down and replication gets started.\nMake sure that the cluster is running in full durable state through the tools and monitoring built."))}h.isMDXComponent=!0}}]);